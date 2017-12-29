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
package mage.game.events;

import java.util.UUID;
import mage.constants.EnterEventType;
import static mage.constants.EnterEventType.SELF;
import mage.constants.Zone;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class EntersTheBattlefieldEvent extends GameEvent {

    private final Zone fromZone;
    private Permanent target;

    public EntersTheBattlefieldEvent(Permanent target, UUID sourceId, UUID playerId, Zone fromZone) {
        this(target, sourceId, playerId, fromZone, EnterEventType.OTHER);
    }

    public EntersTheBattlefieldEvent(Permanent target, UUID sourceId, UUID playerId, Zone fromZone, EnterEventType enterType) {
        super(EventType.ENTERS_THE_BATTLEFIELD, target.getId(), sourceId, playerId);
        switch (enterType) {
            case SELF:
                type = EventType.ENTERS_THE_BATTLEFIELD_SELF;
                break;
            case CONTROL:
                type = EventType.ENTERS_THE_BATTLEFIELD_CONTROL;
                break;
            case COPY:
                type = EventType.ENTERS_THE_BATTLEFIELD_COPY;
                break;
        }
        this.fromZone = fromZone;
        this.target = target;
    }

    public Zone getFromZone() {
        return fromZone;
    }

    public Permanent getTarget() {
        return target;
    }

    public void setTarget(Permanent target) {
        this.target = target;
    }

}
