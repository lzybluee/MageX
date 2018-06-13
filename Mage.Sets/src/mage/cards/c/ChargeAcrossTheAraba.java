
package mage.cards.c;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SweepNumber;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.keyword.SweepEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class ChargeAcrossTheAraba extends CardImpl {

    public ChargeAcrossTheAraba(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{W}");
        this.subtype.add(SubType.ARCANE);

        // Sweep - Return any number of Plains you control to their owner's hand. Creatures you control get +1/+1 until end of turn for each Plains returned this way.
        this.getSpellAbility().addEffect(new SweepEffect(SubType.PLAINS));
        DynamicValue sweepValue = new SweepNumber("Plains");
        this.getSpellAbility().addEffect(new BoostControlledEffect(sweepValue, sweepValue, Duration.EndOfTurn, null, false, true));

    }

    public ChargeAcrossTheAraba(final ChargeAcrossTheAraba card) {
        super(card);
    }

    @Override
    public ChargeAcrossTheAraba copy() {
        return new ChargeAcrossTheAraba(this);
    }
}
