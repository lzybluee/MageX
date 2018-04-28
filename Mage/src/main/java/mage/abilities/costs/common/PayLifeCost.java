/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.CostImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.game.Game;

import java.util.UUID;
import mage.abilities.costs.Cost;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PayLifeCost extends CostImpl {

    private final DynamicValue amount;

    public PayLifeCost(int amount) {
        this.amount = new StaticValue(amount);
        this.text = "Pay " + Integer.toString(amount) + " life";
    }

    public PayLifeCost(DynamicValue amount, String text) {
        this.amount = amount.copy();
        this.text = "Pay " + text;
    }

    public PayLifeCost(PayLifeCost cost) {
        super(cost);
        this.amount = cost.amount.copy();
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        //118.4. If a cost or effect allows a player to pay an amount of life greater than 0, 
        //the player may do so only if their life total is greater than or equal to the
        //amount of the payment. If a player pays life, the payment is subtracted from his or 
        //her life total; in other words, the player loses that much life. (Players can always pay 0 life.)
        int lifeToPayAmount = amount.calculate(game, ability, null);
        if (lifeToPayAmount > 0 && !game.getPlayer(controllerId).canPayLifeCost()) {
            return false;
        }
        return game.getPlayer(controllerId).getLife() >= lifeToPayAmount || lifeToPayAmount == 0;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        int lifeToPayAmount = amount.calculate(game, ability, null);
        this.paid = game.getPlayer(controllerId).loseLife(lifeToPayAmount, game, false) == lifeToPayAmount;
        return paid;
    }

    @Override
    public PayLifeCost copy() {
        return new PayLifeCost(this);
    }

}
