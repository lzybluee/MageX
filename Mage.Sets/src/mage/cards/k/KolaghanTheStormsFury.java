
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.DashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURES;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author LevelX2
 */
public final class KolaghanTheStormsFury extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Dragon you control");

    static {
        filter.add(new SubtypePredicate(SubType.DRAGON));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public KolaghanTheStormsFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a Dragon you control attacks, creatures you control get +1/+0 until end of turn.
        this.addAbility(new AttacksAllTriggeredAbility(
                new BoostControlledEffect(1, 0, Duration.EndOfTurn, FILTER_PERMANENT_CREATURES, false),
                false, filter, SetTargetPointer.NONE, false));

        // Dash {3}{B}{R}
        this.addAbility(new DashAbility(this, "{3}{B}{R}"));
    }

    public KolaghanTheStormsFury(final KolaghanTheStormsFury card) {
        super(card);
    }

    @Override
    public KolaghanTheStormsFury copy() {
        return new KolaghanTheStormsFury(this);
    }
}
