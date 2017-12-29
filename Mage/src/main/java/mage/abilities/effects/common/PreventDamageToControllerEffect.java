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
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;

public class PreventDamageToControllerEffect extends PreventionEffectImpl {

    public PreventDamageToControllerEffect(Duration duration) {
        this(duration, false, false, Integer.MAX_VALUE);
    }

    public PreventDamageToControllerEffect(Duration duration, int amountToPrevent) {
        this(duration, false, true, amountToPrevent);
    }

    public PreventDamageToControllerEffect(Duration duration, boolean onlyCombat, boolean consumable, int amountToPrevent) {
        super(duration, amountToPrevent, onlyCombat, consumable, null);
        staticText = setText();
    }

    public PreventDamageToControllerEffect(Duration duration, boolean onlyCombat, boolean consumable, DynamicValue amountToPreventDynamic) {
        super(duration, 0, onlyCombat, consumable, amountToPreventDynamic);
        staticText = setText();
    }

    public PreventDamageToControllerEffect(final PreventDamageToControllerEffect effect) {
        super(effect);
    }

    @Override
    public PreventDamageToControllerEffect copy() {
        return new PreventDamageToControllerEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (event.getTargetId().equals(source.getControllerId())) {
                return true;
            }

        }
        return false;
    }

    private String setText() {
        // Prevent the next X damage that would be dealt to you this turn
        StringBuilder sb = new StringBuilder("prevent ");
        if (amountToPrevent == Integer.MAX_VALUE) {
            sb.append("all ");
        } else if (amountToPreventDynamic != null) {
            sb.append("the next ").append(amountToPreventDynamic.toString()).append(' ');
        } else {
            sb.append("the next ").append(amountToPrevent).append(' ');
        }
        if (onlyCombat) {
            sb.append("combat ");
        }
        sb.append("damage that would be dealt to you");
        if (duration == Duration.EndOfTurn) {
            sb.append(" this turn");
        }
        return sb.toString();
    }
}
