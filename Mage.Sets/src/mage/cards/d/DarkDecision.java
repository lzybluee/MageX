/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.cards.d;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterNonlandCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Styxo
 */
public class DarkDecision extends CardImpl {

    public DarkDecision(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{R}");

        // As an additional cost to cast Dark Decision, pay 1 life.
        this.getSpellAbility().addCost(new PayLifeCost(1));

        // Search the top 10 cards of your library for a nonland card, exile it, then shuffle your library. Until end of turn, you may cast that card.
        this.getSpellAbility().addEffect(new DarkDecisionEffect());
    }

    public DarkDecision(final DarkDecision card) {
        super(card);
    }

    @Override
    public DarkDecision copy() {
        return new DarkDecision(this);
    }
}

class DarkDecisionEffect extends OneShotEffect {

    public DarkDecisionEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Search the top 10 cards of your library for a nonland card, exile it, then shuffle your library. Until end of turn, you may cast that card";
    }

    public DarkDecisionEffect(final DarkDecisionEffect effect) {
        super(effect);
    }

    @Override
    public DarkDecisionEffect copy() {
        return new DarkDecisionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(new FilterNonlandCard());
            target.setCardLimit(10);
            if (controller.searchLibrary(target, game)) {
                UUID targetId = target.getFirstTarget();
                Card card = game.getCard(targetId);
                if (card != null) {
                    controller.moveCardsToExile(card, source, game, true, source.getSourceId(), sourceObject.getIdName());
                    ContinuousEffect effect = new DarkDecisionMayPlayExiledEffect();
                    effect.setTargetPointer(new FixedTarget(card.getId(), game));
                    game.addEffect(effect, source);
                }
                controller.shuffleLibrary(source, game);
            }
            return true;
        }
        return false;
    }

}

class DarkDecisionMayPlayExiledEffect extends AsThoughEffectImpl {

    public DarkDecisionMayPlayExiledEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
    }

    public DarkDecisionMayPlayExiledEffect(final DarkDecisionMayPlayExiledEffect effect) {
        super(effect);
    }

    @Override
    public DarkDecisionMayPlayExiledEffect copy() {
        return new DarkDecisionMayPlayExiledEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (objectId.equals(getTargetPointer().getFirst(game, source)) && affectedControllerId.equals(source.getControllerId())) {
            ExileZone exileZone = game.getExile().getExileZone(source.getSourceId());
            return exileZone != null && exileZone.contains(getTargetPointer().getFirst(game, source));
        }
        return false;
    }

}
