
package mage.filter.predicate.permanent;

import mage.constants.SubType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author KholdFuzion
 */

public class ControllerControlsIslandPredicate implements Predicate<Permanent> {

    public static final FilterLandPermanent filter = new FilterLandPermanent("Island");
    static {
        filter.add(new SubtypePredicate(SubType.ISLAND));
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        return (game.getBattlefield().countAll(filter, input.getControllerId(), game) > 0);
    }

    @Override
    public String toString() {
        return "Controls an island";
    }
}
