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

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

public class BeginningOfDrawTriggeredAbility extends TriggeredAbilityImpl {

    private TargetController targetController;

    /**
     * The Ability sets if no target is defined the target pointer to the active
     * player of the current draw phase
     *
     * @param effect
     * @param targetController
     * @param isOptional
     */
    public BeginningOfDrawTriggeredAbility(Effect effect, TargetController targetController, boolean isOptional) {
        this(Zone.BATTLEFIELD, effect, targetController, isOptional);
    }

    public BeginningOfDrawTriggeredAbility(Zone zone, Effect effect, TargetController targetController, boolean isOptional) {
        super(zone, effect, isOptional);
        this.targetController = targetController;
    }

    public BeginningOfDrawTriggeredAbility(final BeginningOfDrawTriggeredAbility ability) {
        super(ability);
        this.targetController = ability.targetController;
    }

    @Override
    public BeginningOfDrawTriggeredAbility copy() {
        return new BeginningOfDrawTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (targetController) {
            case YOU:
                boolean yours = event.getPlayerId().equals(this.controllerId);
                if (yours) {
                    if (getTargets().isEmpty()) {
                        this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
                    }
                }
                return yours;
            case OPPONENT:
                if (game.getPlayer(this.controllerId).hasOpponent(event.getPlayerId(), game)) {
                    if (getTargets().isEmpty()) {
                        this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
                    }
                    return true;
                }
                break;
            case NOT_YOU:
                if (!this.controllerId.equals(event.getPlayerId())) {
                    if (getTargets().isEmpty()) {
                        this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
                    }
                    return true;
                }
                break;
            case CONTROLLER_ATTACHED_TO:
                Permanent attachment = game.getPermanent(sourceId);
                if (attachment != null && attachment.getAttachedTo() != null) {
                    Permanent attachedTo = game.getPermanent(attachment.getAttachedTo());
                    if (attachedTo != null && attachedTo.getControllerId().equals(event.getPlayerId())) {
                        if (getTargets().isEmpty()) {
                            this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
                        }
                        return true;
                    }
                }
                break;
            case ANY:
                if (getTargets().isEmpty()) {
                    this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
                }
                return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        switch (targetController) {
            case YOU:
                return "At the beginning of your draw step, " + generateZoneString() + getEffects().getText(modes.getMode());
            case OPPONENT:
                return "At the beginning of each opponent's draw step, " + generateZoneString() + getEffects().getText(modes.getMode());
            case NOT_YOU:
                return "At the beginning of each other player's draw step, " + generateZoneString() + getEffects().getText(modes.getMode());
            case ANY:
                return "At the beginning of each player's draw step, " + generateZoneString() + getEffects().getText(modes.getMode());
            case CONTROLLER_ATTACHED_TO:
                return "At the beginning of the draw step of enchanted creature's controller, " + generateZoneString() + getEffects().getText(modes.getMode());
        }
        return "";
    }

    private String generateZoneString() {
        switch (getZone()) {
            case GRAVEYARD:
                return "if {this} is in your graveyard, ";
        }
        return "";
    }
}
