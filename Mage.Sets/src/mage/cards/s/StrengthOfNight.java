
package mage.cards.s;

import java.util.UUID;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.KickerAbility;
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
public final class StrengthOfNight extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Zombie creatures");

    static {
        filter.add(new SubtypePredicate(SubType.ZOMBIE));
    }

    public StrengthOfNight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}");

        // Kicker {B}
        this.addAbility(new KickerAbility("{B}"));
        // Creatures you control get +1/+1 until end of turn. If Strength of Night was kicked, Zombie creatures you control get an additional +2/+2 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(1, 1, Duration.EndOfTurn));
        ContinuousEffect effect = new BoostControlledEffect(2, 2, Duration.EndOfTurn, filter);
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(effect, new LockedInCondition(KickedCondition.instance),
            "if this spell was kicked, Zombie creatures you control get an additional +2/+2 until end of turn."));
    }

    public StrengthOfNight(final StrengthOfNight card) {
        super(card);
    }

    @Override
    public StrengthOfNight copy() {
        return new StrengthOfNight(this);
    }
}
