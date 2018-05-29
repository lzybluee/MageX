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

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.TimingRule;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward
 */
public final class PastInFlames extends CardImpl {

    public PastInFlames(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}");


        // Each instant and sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost.
        this.getSpellAbility().addEffect(new PastInFlamesEffect());

        // Flashback {4}{R}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl("{4}{R}"), TimingRule.SORCERY));

    }

    public PastInFlames(final PastInFlames card) {
        super(card);
    }

    @Override
    public PastInFlames copy() {
        return new PastInFlames(this);
    }
}

class PastInFlamesEffect extends ContinuousEffectImpl {

    public PastInFlamesEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "Each instant and sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost";
    }

    public PastInFlamesEffect(final PastInFlamesEffect effect) {
        super(effect);
    }

    @Override
    public PastInFlamesEffect copy() {
        return new PastInFlamesEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                player.getGraveyard().stream().map((cardId) -> game.getCard(cardId)).filter((card) -> (card.isInstant() || card.isSorcery())).forEachOrdered((card) -> {
                    affectedObjectList.add(new MageObjectReference(card, game));
                });
             }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.getGraveyard().stream().filter((cardId) -> (affectedObjectList.contains(new MageObjectReference(cardId, game)))).forEachOrdered((cardId) -> {
                Card card = game.getCard(cardId);
                FlashbackAbility ability = null;
                if (card.isInstant()) {
                    ability = new FlashbackAbility(card.getManaCost(), TimingRule.INSTANT);
                }
                else if (card.isSorcery()) {
                    ability = new FlashbackAbility(card.getManaCost(), TimingRule.SORCERY);
                }
                if (ability != null) {
                    ability.setSourceId(cardId);
                    ability.setControllerId(card.getOwnerId());
                    game.getState().addOtherAbility(card, ability);
                }
            });
            return true;
        }
        return false;
    }
}