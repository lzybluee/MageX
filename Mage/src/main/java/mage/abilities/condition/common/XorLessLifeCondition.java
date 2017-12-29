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
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.players.Player;
import mage.players.PlayerList;

import java.util.UUID;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public class XorLessLifeCondition implements Condition {

    public enum CheckType { AN_OPPONENT, CONTROLLER, TARGET_OPPONENT, EACH_PLAYER }

    private final CheckType type;
    private final int amount;

    public XorLessLifeCondition ( CheckType type , int amount) {
        this.type = type;
        this.amount = amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean conditionApplies = false;

        switch ( this.type ) {
            case AN_OPPONENT:
                for ( UUID opponentUUID : game.getOpponents(source.getControllerId()) ) {
                    conditionApplies |= game.getPlayer(opponentUUID).getLife() <= amount;
                }
                break;
            case CONTROLLER:
                conditionApplies = game.getPlayer(source.getControllerId()).getLife() <= amount;
                break;
            case TARGET_OPPONENT:
                //TODO: Implement this.
                break;
            case EACH_PLAYER:
                int maxLife = 0;
                PlayerList playerList = game.getState().getPlayersInRange(source.getControllerId(), game);
                for ( UUID pid : playerList ) {
                    Player p = game.getPlayer(pid);
                    if (p != null) {
                        if (maxLife < p.getLife()) {
                            maxLife = p.getLife();
                        }
                    }
                }
                conditionApplies = maxLife <= amount;
                break;
        }

        return conditionApplies;
    }

}
