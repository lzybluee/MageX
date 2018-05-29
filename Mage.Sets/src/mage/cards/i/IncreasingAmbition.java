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
package mage.cards.i;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.SearchEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author BetaSteward
 */
public final class IncreasingAmbition extends CardImpl {

    public IncreasingAmbition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}");


        // Search your library for a card and put that card into your hand. If Increasing Ambition was cast from a graveyard, instead search your library for two cards and put those cards into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new IncreasingAmbitionEffect());

        // Flashback {7}{B}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl("{7}{B}"), TimingRule.SORCERY));
    }

    public IncreasingAmbition(final IncreasingAmbition card) {
        super(card);
    }

    @Override
    public IncreasingAmbition copy() {
        return new IncreasingAmbition(this);
    }
}

class IncreasingAmbitionEffect extends SearchEffect {

    public IncreasingAmbitionEffect() {
        super(new TargetCardInLibrary(), Outcome.DrawCard);
        staticText = "Search your library for a card and put that card into your hand. If {this} was cast from a graveyard, instead search your library for two cards and put those cards into your hand. Then shuffle your library";
    }

    public IncreasingAmbitionEffect(final IncreasingAmbitionEffect effect) {
        super(effect);
    }

    @Override
    public IncreasingAmbitionEffect copy() {
        return new IncreasingAmbitionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Spell spell = (Spell) game.getStack().getStackObject(source.getSourceId());
            if (spell != null) {
                if (spell.getFromZone() == Zone.GRAVEYARD) {
                    target = new TargetCardInLibrary(2, new FilterCard());
                }
                else {
                    target = new TargetCardInLibrary();
                }
                if (player.searchLibrary(target, game)) {
                    if (!target.getTargets().isEmpty()) {
                        for (UUID cardId: (List<UUID>)target.getTargets()) {
                            Card card = player.getLibrary().remove(cardId, game);
                            if (card != null) {
                                card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                            }
                        }
                    }
                }
                // shuffle anyway
                player.shuffleLibrary(source, game);
                return true;
            }
        }
        return false;
    }

}
