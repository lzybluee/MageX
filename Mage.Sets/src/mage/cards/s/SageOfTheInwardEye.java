
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author LevelX2
 */
public final class SageOfTheInwardEye extends CardImpl {

    private static final FilterSpell filterNonCreature = new FilterSpell("a noncreature spell");

    static {
        filterNonCreature.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    public SageOfTheInwardEye(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}{W}");
        this.subtype.add(SubType.DJINN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever you cast a noncreature spell, creatures you control gain lifelink until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new GainAbilityControlledEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE, false),
                filterNonCreature, false));

    }

    public SageOfTheInwardEye(final SageOfTheInwardEye card) {
        super(card);
    }

    @Override
    public SageOfTheInwardEye copy() {
        return new SageOfTheInwardEye(this);
    }
}
