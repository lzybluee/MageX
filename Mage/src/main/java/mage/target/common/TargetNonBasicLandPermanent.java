

package mage.target.common;

import mage.constants.SuperType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SupertypePredicate;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TargetNonBasicLandPermanent extends TargetLandPermanent {

    public TargetNonBasicLandPermanent() {
        this.filter = new FilterLandPermanent();
        this.filter.add(Predicates.not(new SupertypePredicate(SuperType.BASIC)));
        this.targetName = "nonbasic land";
    }

    public TargetNonBasicLandPermanent(final TargetNonBasicLandPermanent target) {
        super(target);
    }

    @Override
    public TargetNonBasicLandPermanent copy() {
        return new TargetNonBasicLandPermanent(this);
    }
}
