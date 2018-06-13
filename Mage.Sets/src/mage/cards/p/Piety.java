
package mage.cards.p;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterBlockingCreature;

/**
 *
 * @author MarcoMarin
 */
public final class Piety extends CardImpl {

    static final FilterBlockingCreature filter = new FilterBlockingCreature();
    
    public Piety(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");

        // Blocking creatures get +0/+3 until end of turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(0, 3, Duration.EndOfTurn, filter, false)));
        //this.addAbility(new OnEventTriggeredAbility(GameEvent.EventType.END_TURN_STEP_POST, "end Piety", true, new BoostAllEffect(0, 3, Duration.EndOfTurn, filter, false)));
    }

    public Piety(final Piety card) {
        super(card);
    }

    @Override
    public Piety copy() {
        return new Piety(this);
    }
}
