
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author KholdFuzion
 */
public final class BatteringSliver extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("all Sliver creatures");

    static {
        filter.add(new SubtypePredicate(SubType.SLIVER));
    }

    public BatteringSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // All Sliver creatures have trample.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAllEffect(TrampleAbility.getInstance(),
                        Duration.WhileOnBattlefield, filter)));
    }

    public BatteringSliver(final BatteringSliver card) {
        super(card);
    }

    @Override
    public BatteringSliver copy() {
        return new BatteringSliver(this);
    }
}
