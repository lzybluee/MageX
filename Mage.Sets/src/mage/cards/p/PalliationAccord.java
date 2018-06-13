
package mage.cards.p;

import java.util.UUID;
import mage.abilities.common.BecomesTappedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.PreventDamageToControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author fireshoes
 */
public final class PalliationAccord extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature an opponent controls");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public PalliationAccord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{U}");

        // Whenever a creature an opponent controls becomes tapped, put a shield counter on Palliation Accord.
        this.addAbility(new BecomesTappedTriggeredAbility(new AddCountersSourceEffect(CounterType.SHIELD.createInstance()), false, filter));

        // Remove a shield counter from Palliation Accord: Prevent the next 1 damage that would be dealt to you this turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new PreventDamageToControllerEffect(Duration.EndOfTurn, 1),
                new RemoveCountersSourceCost(CounterType.SHIELD.createInstance())));
    }

    public PalliationAccord(final PalliationAccord card) {
        super(card);
    }

    @Override
    public PalliationAccord copy() {
        return new PalliationAccord(this);
    }
}
