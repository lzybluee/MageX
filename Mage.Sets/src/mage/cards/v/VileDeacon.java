
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author LoneFox
 */
public final class VileDeacon extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Clerics");

    static {
        filter.add(new SubtypePredicate(SubType.CLERIC));
    }

    public VileDeacon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Vile Deacon attacks, it gets +X/+X until end of turn, where X is the number of Clerics on the battlefield.
        PermanentsOnBattlefieldCount amount = new PermanentsOnBattlefieldCount(filter);
        Effect effect = new BoostSourceEffect(amount, amount, Duration.EndOfTurn, true);
        effect.setText("it gets +X/+X until end of turn, where X is the number of Clerics on the battlefield");
        this.addAbility(new AttacksTriggeredAbility(effect, false));
    }

    public VileDeacon(final VileDeacon card) {
        super(card);
    }

    @Override
    public VileDeacon copy() {
        return new VileDeacon(this);
    }
}
