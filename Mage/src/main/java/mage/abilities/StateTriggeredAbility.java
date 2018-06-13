
package mage.abilities;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class StateTriggeredAbility extends TriggeredAbilityImpl {

    public StateTriggeredAbility(Zone zone, Effect effect) {
        super(zone, effect);
    }

    public StateTriggeredAbility(final StateTriggeredAbility ability) {
        super(ability);
    }

    public boolean canTrigger(Game game) {
        //20100716 - 603.8
        Boolean triggered = (Boolean) game.getState().getValue(getSourceId().toString() + "triggered");
        if (triggered == null) {
            triggered = Boolean.FALSE;
        }
        return !triggered;
    }

    @Override
    public final boolean checkEventType(GameEvent event, Game game) {
        return false;
    }

    @Override
    public void trigger(Game game, UUID controllerId) {
        //20100716 - 603.8
        game.getState().setValue(this.getSourceId().toString() + "triggered", Boolean.TRUE);
        super.trigger(game, controllerId);
    }

    @Override
    public boolean resolve(Game game) {
        //20100716 - 603.8
        boolean result = super.resolve(game);
        game.getState().setValue(this.getSourceId().toString() + "triggered", Boolean.FALSE);
        return result;
    }

    public void counter(Game game) {
        game.getState().setValue(this.getSourceId().toString() + "triggered", Boolean.FALSE);
    }
}
