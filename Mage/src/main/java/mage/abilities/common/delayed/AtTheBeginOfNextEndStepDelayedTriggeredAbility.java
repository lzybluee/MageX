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
package mage.abilities.common.delayed;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.Effect;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public class AtTheBeginOfNextEndStepDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private TargetController targetController;
    private Condition condition;

    public AtTheBeginOfNextEndStepDelayedTriggeredAbility(Effect effect) {
        this(effect, TargetController.ANY);
    }

    public AtTheBeginOfNextEndStepDelayedTriggeredAbility(Effect effect, TargetController targetController) {
        this(Zone.ALL, effect, targetController);
    }

    public AtTheBeginOfNextEndStepDelayedTriggeredAbility(Zone zone, Effect effect, TargetController targetController) {
        this(zone, effect, targetController, null);
    }

    public AtTheBeginOfNextEndStepDelayedTriggeredAbility(Zone zone, Effect effect, TargetController targetController, Condition condition) {
        super(effect, Duration.Custom);
        this.zone = zone;
        this.targetController = targetController;
        this.condition = condition;
    }

    public AtTheBeginOfNextEndStepDelayedTriggeredAbility(final AtTheBeginOfNextEndStepDelayedTriggeredAbility ability) {
        super(ability);
        this.targetController = ability.targetController;
        this.condition = ability.condition;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.END_TURN_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        boolean correctEndPhase = false;
        switch (targetController) {
            case ANY:
                correctEndPhase = true;
                break;
            case YOU:
                correctEndPhase = event.getPlayerId().equals(this.controllerId);
                break;
            case OPPONENT:
                if (game.getPlayer(this.getControllerId()).hasOpponent(event.getPlayerId(), game)) {
                    correctEndPhase = true;
                }
                break;
            case CONTROLLER_ATTACHED_TO:
                Permanent attachment = game.getPermanent(sourceId);
                if (attachment != null && attachment.getAttachedTo() != null) {
                    Permanent attachedTo = game.getPermanent(attachment.getAttachedTo());
                    if (attachedTo != null && attachedTo.getControllerId().equals(event.getPlayerId())) {
                        correctEndPhase = true;
                    }
                }
        }
        if (correctEndPhase) {
            return !(condition != null && !condition.apply(game, this));
        }
        return false;
    }

    @Override
    public AtTheBeginOfNextEndStepDelayedTriggeredAbility copy() {
        return new AtTheBeginOfNextEndStepDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder();
        switch (targetController) {
            case YOU:
                sb.append("At the beginning of your next end step, ");
                break;
            case OPPONENT:
                sb.append("At the beginning of an opponent's next end step, ");
                break;
            case ANY:
                sb.append("At the beginning of the next end step, ");
                break;
            case CONTROLLER_ATTACHED_TO:
                sb.append("At the beginning of the next end step of enchanted creature's controller, ");
                break;
        }
        sb.append(getEffects().getText(modes.getMode()));
        return sb.toString();
    }
}
