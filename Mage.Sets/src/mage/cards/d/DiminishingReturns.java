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
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Quercitron
 */
public final class DiminishingReturns extends CardImpl {

    public DiminishingReturns(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}{U}");

        // Each player shuffles their hand and graveyard into their library. You exile the top ten cards of your library. Then each player draws up to seven cards.
        this.getSpellAbility().addEffect(new DiminishingReturnsEffect());
    }

    public DiminishingReturns(final DiminishingReturns card) {
        super(card);
    }

    @Override
    public DiminishingReturns copy() {
        return new DiminishingReturns(this);
    }
}

class DiminishingReturnsEffect extends OneShotEffect {

    public DiminishingReturnsEffect() {
        super(Outcome.Neutral);
        staticText = "Each player shuffles their hand and graveyard into their library. You exile the top ten cards of your library. Then each player draws up to seven cards.";
    }

    public DiminishingReturnsEffect(final DiminishingReturnsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    for (Card card: player.getHand().getCards(game)) {
                        card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                    }                    
                    for (Card card: player.getGraveyard().getCards(game)) {
                        card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                    }                    
                    player.shuffleLibrary(source, game);
                }
            }

            for (Card card: controller.getLibrary().getTopCards(game, 10)) {
                controller.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.LIBRARY, true);
            }

            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int cardsToDrawCount = player.getAmount(0, 7, "How many cards to draw (up to 7)?", game);
                    player.drawCards(cardsToDrawCount, game);
                }
            }
        }
        return true;
    }

    @Override
    public DiminishingReturnsEffect copy() {
        return new DiminishingReturnsEffect(this);
    }
}
