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
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class RepayInKind extends CardImpl {

    public RepayInKind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{B}{B}");


        // Each player's life total becomes the lowest life total among all players.
        this.getSpellAbility().addEffect(new RepayInKindEffect());
    }

    public RepayInKind(final RepayInKind card) {
        super(card);
    }

    @Override
    public RepayInKind copy() {
        return new RepayInKind(this);
    }
}

class RepayInKindEffect extends OneShotEffect {

    public RepayInKindEffect() {
        super(Outcome.Tap);
        staticText = "Each player's life total becomes the lowest life total among all players";
    }

    public RepayInKindEffect(final RepayInKindEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int lowestLife = game.getPlayer(source.getControllerId()).getLife();
        for (Player playerid : game.getPlayers().values()) {
            if (playerid != null) {
                if (lowestLife > playerid.getLife()) {
                    lowestLife = playerid.getLife();
                }
            }  
        }
        for (Player playerId : game.getPlayers().values()) {
            if (playerId != null) {
                playerId.setLife(lowestLife, game, source);
            }
        }
        return true;
    }

    @Override
    public RepayInKindEffect copy() {
        return new RepayInKindEffect(this);
    }

}
