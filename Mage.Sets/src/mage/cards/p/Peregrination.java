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
package mage.cards.p;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class Peregrination extends CardImpl {

    public Peregrination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}");

        // Seach your library for up to two basic land cards, reveal those cards, and put one onto the battlefield tapped and the other into your hand. Shuffle your library, then scry 1.
        this.getSpellAbility().addEffect(new PeregrinationEffect());
        Effect effect = new ScryEffect(1);
        effect.setText("then scry 1  <i>(Look at the top card of your library. You may put that card on the bottom of your library.)</i>");
        this.getSpellAbility().addEffect(effect);
    }

    public Peregrination(final Peregrination card) {
        super(card);
    }

    @Override
    public Peregrination copy() {
        return new Peregrination(this);
    }
}

class PeregrinationEffect extends OneShotEffect {

    protected static final FilterCard filter = new FilterCard("card to put on the battlefield tapped");

    public PeregrinationEffect() {
        super(Outcome.PutLandInPlay);
        staticText = "Search your library for up to two basic land cards, reveal those cards, and put one onto the battlefield tapped and the other into your hand. Shuffle your library";
    }

    public PeregrinationEffect(final PeregrinationEffect effect) {
        super(effect);
    }

    @Override
    public PeregrinationEffect copy() {
        return new PeregrinationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller == null || sourceObject == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(0, 2, StaticFilters.FILTER_BASIC_LAND_CARD);
        if (controller.searchLibrary(target, game)) {
            if (!target.getTargets().isEmpty()) {
                Cards revealed = new CardsImpl();
                for (UUID cardId : target.getTargets()) {
                    Card card = controller.getLibrary().getCard(cardId, game);
                    revealed.add(card);
                }
                controller.revealCards(sourceObject.getIdName(), revealed, game);
                if (target.getTargets().size() == 2) {
                    TargetCard target2 = new TargetCard(Zone.LIBRARY, filter);
                    controller.choose(Outcome.Benefit, revealed, target2, game);
                    Card card = revealed.get(target2.getFirstTarget(), game);
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
                    revealed.remove(card);
                    card = revealed.getCards(game).iterator().next();
                    controller.moveCards(card, Zone.HAND, source, game);
                } else if (target.getTargets().size() == 1) {
                    Card card = revealed.getCards(game).iterator().next();
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
                }

            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        controller.shuffleLibrary(source, game);
        return false;

    }

}
