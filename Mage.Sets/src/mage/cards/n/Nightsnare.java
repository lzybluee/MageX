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
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class Nightsnare extends CardImpl {

    public Nightsnare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}");

        // Target opponent reveals their hand. You may choose a nonland card from it. If you do, that player discards that card. If you don't, that player discards two cards.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new NightsnareDiscardEffect());
    }

    public Nightsnare(final Nightsnare card) {
        super(card);
    }

    @Override
    public Nightsnare copy() {
        return new Nightsnare(this);
    }
}

class NightsnareDiscardEffect extends OneShotEffect {

    public NightsnareDiscardEffect() {
        super(Outcome.Discard);
        staticText = "Target opponent reveals their hand. You may choose a nonland card from it. If you do, that player discards that card. If you don't, that player discards two cards";
    }

    public NightsnareDiscardEffect(final NightsnareDiscardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        Card sourceCard = game.getCard(source.getSourceId());
        if (player != null && controller != null) {
            if (!player.getHand().isEmpty()) {
                Cards revealedCards = new CardsImpl();
                revealedCards.addAll(player.getHand());
                player.revealCards(sourceCard != null ? sourceCard.getIdName() : "Discard", revealedCards, game);
                // You may choose a nonland card from it.
                if (controller.chooseUse(outcome, "Choose a a card to discard? (Otherwise " + player.getLogName() + " has to discard 2 cards).", source, game)) {
                    TargetCard target = new TargetCard(1, Zone.HAND, new FilterNonlandCard());
                    if (controller.choose(Outcome.Benefit, revealedCards, target, game)) {
                        for (Object targetId : target.getTargets()) {
                            Card card = revealedCards.get((UUID) targetId, game);
                            if (card != null) {
                                player.discard(card, source, game);
                            }
                        }
                    }

                } else {
                    player.discard(2, false, source, game);
                }
            }
            return true;

        }
        return false;

    }

    @Override
    public NightsnareDiscardEffect copy() {
        return new NightsnareDiscardEffect(this);
    }

}
