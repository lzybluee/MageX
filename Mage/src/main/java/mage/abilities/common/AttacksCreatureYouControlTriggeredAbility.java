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
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author noxx
 */
public class AttacksCreatureYouControlTriggeredAbility extends TriggeredAbilityImpl {

    protected FilterControlledCreaturePermanent filter;
    protected boolean setTargetPointer;
    protected boolean once = false;

    public AttacksCreatureYouControlTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public AttacksCreatureYouControlTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, new FilterControlledCreaturePermanent());
    }

    public AttacksCreatureYouControlTriggeredAbility(Effect effect, boolean optional, boolean setTargetPointer) {
        this(effect, optional, new FilterControlledCreaturePermanent(), setTargetPointer);
    }

    public AttacksCreatureYouControlTriggeredAbility(Effect effect, boolean optional, FilterControlledCreaturePermanent filter) {
        this(effect, optional, filter, false);
    }

    public AttacksCreatureYouControlTriggeredAbility(Effect effect, boolean optional, FilterControlledCreaturePermanent filter, boolean setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
    }

    public AttacksCreatureYouControlTriggeredAbility(AttacksCreatureYouControlTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.setTargetPointer = ability.setTargetPointer;
    }

    public void setOnce(boolean once) {
        this.once = once;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent sourcePermanent = game.getPermanent(event.getSourceId());
        if (sourcePermanent != null && filter.match(sourcePermanent, sourceId, controllerId, game)) {
            if (setTargetPointer) {
                this.getEffects().setTargetPointer(new FixedTarget(event.getSourceId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public AttacksCreatureYouControlTriggeredAbility copy() {
        return new AttacksCreatureYouControlTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When" + (once ? "" : "ever") + " a" + (filter.getMessage().startsWith("a") ? "n " : " ") + filter.getMessage() + " attacks, " + super.getRule();
    }
}
