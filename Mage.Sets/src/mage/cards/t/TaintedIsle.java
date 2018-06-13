
package mage.cards.t;

import mage.Mana;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.mana.ActivateIfConditionManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class TaintedIsle extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("you control a swamp");
    static {
        filter.add(new SubtypePredicate(SubType.SWAMP));
    }

    public TaintedIsle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {tap}: Add {U} or {B}. Activate this ability only if you control a Swamp.
        this.addAbility(new ActivateIfConditionManaAbility(
                Zone.BATTLEFIELD,
                new BasicManaEffect(Mana.BlueMana(1)),
                new TapSourceCost(),
                new PermanentsOnTheBattlefieldCondition(filter)));
        this.addAbility(new ActivateIfConditionManaAbility(
                Zone.BATTLEFIELD,
                new BasicManaEffect(Mana.BlackMana(1)),
                new TapSourceCost(),
                new PermanentsOnTheBattlefieldCondition(filter)));

    }

    public TaintedIsle(final TaintedIsle card) {
        super(card);
    }

    @Override
    public TaintedIsle copy() {
        return new TaintedIsle(this);
    }
}
