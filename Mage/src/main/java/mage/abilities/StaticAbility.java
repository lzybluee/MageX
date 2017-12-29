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
package mage.abilities;

import mage.MageObject;
import mage.abilities.effects.Effect;
import mage.constants.AbilityType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class StaticAbility extends AbilityImpl {

    protected StaticAbility(AbilityType abilityType, Zone zone) {
        super(abilityType, zone);
    }

    public StaticAbility(Zone zone, Effect effect) {
        super(AbilityType.STATIC, zone);
        if (effect != null) {
            this.addEffect(effect);
        }
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject source, GameEvent event) {
        if (game.getShortLivingLKI(getSourceId(), zone)) {
            return true; // maybe this can be a problem if effects removed the ability from the object
        }
        if (game.getPermanentEntering(getSourceId()) != null && zone == Zone.BATTLEFIELD) {
            return true; // abilities of permanents entering battlefield are countes as on battlefield
        }
        return super.isInUseableZone(game, source, event);
    }

    public StaticAbility(StaticAbility ability) {
        super(ability);
    }
}
