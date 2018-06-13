
package mage.abilities.mana;


import mage.constants.Zone;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ManaEffect;
import mage.constants.Duration;

/**
 * @author Plopman
 */
public abstract class DelayedTriggeredManaAbility extends DelayedTriggeredAbility {

    public DelayedTriggeredManaAbility(Zone zone, ManaEffect effect) {
        super(effect);
    }

    public DelayedTriggeredManaAbility(final DelayedTriggeredManaAbility ability) {
        super(ability);
    }
    
    public DelayedTriggeredManaAbility(Effect effect, Duration duration, Boolean triggerOnlyOnce) {
        super(effect, duration, triggerOnlyOnce);
    }
}
