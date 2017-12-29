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
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public class SweepNumber implements DynamicValue {

    private int zoneChangeCounter = 0;
    private final String sweepSubtype;
    private final boolean previousZone;

    public SweepNumber(String sweepSubtype, boolean previousZone) {
        this.sweepSubtype = sweepSubtype;
        this.previousZone = previousZone;
    }

    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        if (zoneChangeCounter == 0) {
            Card card = game.getCard(source.getSourceId());
            if (card != null) {
                zoneChangeCounter = card.getZoneChangeCounter(game);
                if (previousZone) {
                    zoneChangeCounter--;
                }
            }
        }
        int number = 0;
        Integer sweepNumber = (Integer) game.getState().getValue(new StringBuilder("sweep").append(source.getSourceId()).append(zoneChangeCounter).toString());
        if (sweepNumber != null) {
            number = sweepNumber;
        }
        return number;
    }

    @Override
    public SweepNumber copy() {
        return new SweepNumber(sweepSubtype, previousZone);
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return new StringBuilder("the number of ").append(sweepSubtype).append(sweepSubtype.endsWith("s") ? "":"s").append(" returned this way").toString();
    }
}
