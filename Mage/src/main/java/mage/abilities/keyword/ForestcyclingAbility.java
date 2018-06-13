

package mage.abilities.keyword;

import mage.abilities.costs.mana.ManaCosts;
import mage.constants.SubType;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author Plopman
 */
public class ForestcyclingAbility extends CyclingAbility{
    private static final FilterLandCard filter = new FilterLandCard("Forest card");
    private static final String text = "Forestcycling";
    static{
        filter.add(new SubtypePredicate(SubType.FOREST));
    }

    public ForestcyclingAbility(ManaCosts costs) {
        super(costs, filter, text);
    }

    public ForestcyclingAbility(final ForestcyclingAbility ability) {
        super(ability);
    }

    @Override
    public ForestcyclingAbility copy() {
        return new ForestcyclingAbility(this);
    }
}
