package mage.player.ai.ma.optimizers.impl;

import java.util.List;
import mage.abilities.Ability;
import mage.game.Game;

/**
 * Removes abilities that require only discard a card for activation.
 *
 * @author magenoxx_at_gmail.com
 */
public class DiscardCardOptimizer extends BaseTreeOptimizer {

    @Override
    public void filter(Game game, List<Ability> actions) {
        for (Ability ability : actions) {
            if (ability.toString().startsWith("Discard card")) {
                removeAbility(ability);
            }
        }
    }
}
