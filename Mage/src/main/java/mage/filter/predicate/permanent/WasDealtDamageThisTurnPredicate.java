
package mage.filter.predicate.permanent;

import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public class WasDealtDamageThisTurnPredicate implements Predicate<Permanent> {

    @Override
    public boolean apply(Permanent input, Game game) {
        return !input.getDealtDamageByThisTurn().isEmpty();
    }

    @Override
    public String toString() {
        return "WasDealtDamageThisTurn";
    }
}
