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

package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author L_J
 */

public final class HonorTheFallen extends CardImpl {

    public HonorTheFallen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Exile all creature cards from all graveyards. You gain 1 life for each card exiled this way.
        this.getSpellAbility().addEffect(new HonorTheFallenEffect());
    }

    public HonorTheFallen(final HonorTheFallen card) {
        super(card);
    }

    @Override
    public HonorTheFallen copy() {
        return new HonorTheFallen(this);
    }

}

class HonorTheFallenEffect extends OneShotEffect {

    private static final FilterCreatureCard filter = new FilterCreatureCard();

    public HonorTheFallenEffect() {
        super(Outcome.Detriment);
        staticText = "Exile all creature cards from all graveyards. You gain 1 life for each card exiled this way";
    }

    public HonorTheFallenEffect(final HonorTheFallenEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int exiledCards = 0;
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    for (Card card: player.getGraveyard().getCards(game)) {
                        if (filter.match(card, game)) {
                            if (card.moveToExile(null, "", source.getSourceId(), game)) {
                                exiledCards++;
                            }
                        }
                    }
                }
            }
            controller.gainLife(exiledCards, game, source);
            return true;
        }
        return false;
    }

    @Override
    public HonorTheFallenEffect copy() {
        return new HonorTheFallenEffect(this);
    }

}
