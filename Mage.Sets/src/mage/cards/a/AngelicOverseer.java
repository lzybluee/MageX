
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author nantuko
 */
public final class AngelicOverseer extends CardImpl {

    private static final String rule1 = "As long as you control a Human, {this} has hexproof";
    private static final String rule2 = "and is indestructible";
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Human");

    static {
        filter.add(new SubtypePredicate(SubType.HUMAN));
    }

    public AngelicOverseer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.color.setWhite(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());

        // As long as you control a Human, Angelic Overseer has hexproof and is indestructible.
        ConditionalContinuousEffect effect1 = new ConditionalContinuousEffect(new GainAbilitySourceEffect(HexproofAbility.getInstance()), new PermanentsOnTheBattlefieldCondition(filter), rule1);
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect1);
        ConditionalContinuousEffect effect2 = new ConditionalContinuousEffect(new GainAbilitySourceEffect(IndestructibleAbility.getInstance()), new PermanentsOnTheBattlefieldCondition(filter), rule2);
        ability.addEffect(effect2);
        this.addAbility(ability);
    }

    public AngelicOverseer(final AngelicOverseer card) {
        super(card);
    }

    @Override
    public AngelicOverseer copy() {
        return new AngelicOverseer(this);
    }
}
