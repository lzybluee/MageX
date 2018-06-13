
package mage.abilities.effects.common.discard;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */


public class DiscardHandControllerEffect extends OneShotEffect {

    public DiscardHandControllerEffect() {
        super(Outcome.Discard);
        this.staticText = "Discard your hand";
    }

    public DiscardHandControllerEffect(final DiscardHandControllerEffect effect) {
        super(effect);
    }

    @Override
    public DiscardHandControllerEffect copy() {
        return new DiscardHandControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (Card card : player.getHand().getCards(game)) {
                player.discard(card, source, game);
            }
            return true;
        }
        return false;
    }
}
