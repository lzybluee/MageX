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
package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.cards.SplitCardHalf;
import mage.constants.Zone;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 *
 * @author jeffwadsworth
 */
public class MulticoloredPredicate implements Predicate<MageObject> {

    @Override
    public boolean apply(MageObject input, Game game) {
        // 708.3. Each split card that consists of two halves with different colored mana symbols in their mana costs
        // is a multicolored card while it’s not a spell on the stack. While it’s a spell on the stack, it’s only the
        // color or colors of the half or halves being cast. #
        if (input instanceof SplitCardHalf && game.getState().getZone(input.getId()) != Zone.STACK) {
            return 1 < ((SplitCardHalf) input).getMainCard().getColor(game).getColorCount();
        } else {
            return 1 < input.getColor(game).getColorCount();
        }
    }

    @Override
    public String toString() {
        return "Multicolored";
    }
}
