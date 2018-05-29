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
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Quercitron
 */
public final class NaturesResurgence extends CardImpl {

    public NaturesResurgence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}{G}");


        // Each player draws a card for each creature card in their graveyard.
        this.getSpellAbility().addEffect(new NaturesResurgenceEffect());
    }

    public NaturesResurgence(final NaturesResurgence card) {
        super(card);
    }

    @Override
    public NaturesResurgence copy() {
        return new NaturesResurgence(this);
    }
}

class NaturesResurgenceEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCreatureCard("creature");
    
    public NaturesResurgenceEffect() {
        super(Outcome.DrawCard);
        staticText = "Each player draws a card for each creature card in their graveyard";
    }

    public NaturesResurgenceEffect(final NaturesResurgenceEffect effect) {
        super(effect);
    }
    
    @Override
    public NaturesResurgenceEffect copy() {
        return new NaturesResurgenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player sourcePlayer = game.getPlayer(source.getControllerId());
        if (sourcePlayer != null) {
            for (UUID playerId : game.getState().getPlayersInRange(sourcePlayer.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int amount = player.getGraveyard().count(filter, game);
                    player.drawCards(amount, game);
                }
            }
        }
        return true;
    }
}
