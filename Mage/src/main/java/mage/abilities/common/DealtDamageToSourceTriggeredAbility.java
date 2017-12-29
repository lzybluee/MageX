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
package mage.abilities.common;

import mage.constants.Zone;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.DamagedCreatureEvent;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public class DealtDamageToSourceTriggeredAbility extends TriggeredAbilityImpl {

    private final boolean enrage;
    private final boolean useValue;
    private boolean usedForCombatDamageStep;

    public DealtDamageToSourceTriggeredAbility(Zone zone, Effect effect, boolean optional) {
        this(zone, effect, optional, false);
    }

    public DealtDamageToSourceTriggeredAbility(Zone zone, Effect effect, boolean optional, boolean enrage) {
        this(zone, effect, optional, enrage, false);
    }

    public DealtDamageToSourceTriggeredAbility(Zone zone, Effect effect, boolean optional, boolean enrage, boolean useValue) {
        super(zone, effect, optional);
        this.enrage = enrage;
        this.useValue = useValue;
        this.usedForCombatDamageStep = false;
    }

    public DealtDamageToSourceTriggeredAbility(final DealtDamageToSourceTriggeredAbility ability) {
        super(ability);
        this.enrage = ability.enrage;
        this.useValue = ability.useValue;
        this.usedForCombatDamageStep = ability.usedForCombatDamageStep;
    }

    @Override
    public DealtDamageToSourceTriggeredAbility copy() {
        return new DealtDamageToSourceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_CREATURE || event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_POST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_CREATURE && event.getTargetId().equals(getSourceId())) {
            if (useValue) {
//              TODO: this ability should only trigger once for multiple creatures dealing combat damage.  
//              If the damaged creature uses the amount (e.g. Boros Reckoner), this will still trigger separately instead of all at once
                for (Effect effect : this.getEffects()) {
                    effect.setValue("damage", event.getAmount());
                }
                return true;
            } else {
                if (((DamagedCreatureEvent) event).isCombatDamage()) {
                    if (!usedForCombatDamageStep) {
                        usedForCombatDamageStep = true;
                        return true;
                    }
                } else {
                    return true;
                }
            }
        }
        if (event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_POST) {
            usedForCombatDamageStep = false;
        }
        return false;
    }

    @Override
    public String getRule() {
        return (enrage ? "<i>Enrage</i> &mdash; " : "") + "Whenever {this} is dealt damage, " + super.getRule();
    }
}
