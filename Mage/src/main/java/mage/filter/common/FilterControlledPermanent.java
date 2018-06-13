

package mage.filter.common;

import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FilterControlledPermanent extends FilterPermanent {

    public FilterControlledPermanent() {
        this("permanent you control");
    }

    public FilterControlledPermanent(String name) {
        super(name);
        this.add(new ControllerPredicate(TargetController.YOU));
    }

    public FilterControlledPermanent(final FilterControlledPermanent filter) {
        super(filter);
    }

    @Override
    public FilterControlledPermanent copy() {
        return new FilterControlledPermanent(this);
    }

}
