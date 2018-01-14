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

import mage.constants.Zone;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SimpleTriggeredAbility extends TriggeredAbilityImpl {

    private EventType eventType;
    private boolean onlyController;
    private String prefix;

    public SimpleTriggeredAbility(Zone zone, EventType eventType, Effect effect, String prefix) {
        this(zone, eventType, effect, prefix, false);
    }

    public SimpleTriggeredAbility(Zone zone, EventType eventType, Effect effect, String prefix, boolean onlyController) {
        this(zone, eventType, effect, prefix, onlyController, false);
    }

    public SimpleTriggeredAbility(Zone zone, EventType eventType, Effect effect, String prefix, boolean onlyController, boolean optional) {
        super(zone, effect, optional);
        this.eventType = eventType;
        this.onlyController = onlyController;
        this.prefix = prefix;
    }

    public SimpleTriggeredAbility(final SimpleTriggeredAbility ability) {
        super(ability);
        this.eventType = ability.eventType;
        this.onlyController = ability.onlyController;
        this.prefix = ability.prefix;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == eventType;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return !onlyController || event.getPlayerId().equals(this.controllerId);
    }

    @Override
    public SimpleTriggeredAbility copy() {
        return new SimpleTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return prefix + super.getRule();
    }
}
