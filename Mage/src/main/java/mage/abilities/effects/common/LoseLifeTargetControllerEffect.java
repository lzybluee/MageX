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
package mage.abilities.effects.common;

import mage.constants.Outcome;
import mage.constants.Zone;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author North
 */
public class LoseLifeTargetControllerEffect extends OneShotEffect {

    protected int amount;

    public LoseLifeTargetControllerEffect(int amount) {
        super(Outcome.Damage);
        this.amount = amount;
        staticText = "Its controller loses " + amount + " life";
    }

    public LoseLifeTargetControllerEffect(final LoseLifeTargetControllerEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public LoseLifeTargetControllerEffect copy() {
        return new LoseLifeTargetControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject targetCard = game.getLastKnownInformation(targetPointer.getFirst(game, source), Zone.BATTLEFIELD);

        if ( targetCard == null ) {
            MageObject obj = game.getObject(targetPointer.getFirst(game, source));
            if ( obj instanceof Card ) {
                targetCard = (Card)obj;
            } else {
                // if target is a countered spell
                targetCard = game.getLastKnownInformation(targetPointer.getFirst(game, source), Zone.STACK);
            }
        }

        if ( targetCard != null ) {
            Player controller = null;

            //Handles interaction with permanents that were on the battlefield.
            if ( targetCard instanceof Permanent ) {
                Permanent targetPermanent = (Permanent)targetCard;
                controller = game.getPlayer(targetPermanent.getControllerId());
            }
            //Handles interaction with spells that were on the stack.
            else if ( targetCard instanceof Spell ) {
                Spell targetSpell = (Spell)targetCard;
                controller = game.getPlayer(targetSpell.getControllerId());
            }

            if ( controller != null ) {
                controller.loseLife(amount, game, false);
                return true;
            }
        }
        return false;
    }

}
