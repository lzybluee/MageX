
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class SilvergillDouser extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Merfolk and/or Faeries you control");

    static {
        filter.add(Predicates.or(new SubtypePredicate(SubType.MERFOLK), new SubtypePredicate(SubType.FAERIE)));
    }

    public SilvergillDouser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Target creature gets -X/-0 until end of turn, where X is the number of Merfolk and/or Faeries you control.
        DynamicValue number = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(filter), -1);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(number, new StaticValue(0), Duration.EndOfTurn, true), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public SilvergillDouser(final SilvergillDouser card) {
        super(card);
    }

    @Override
    public SilvergillDouser copy() {
        return new SilvergillDouser(this);
    }
}
