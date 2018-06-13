
package mage.abilities.effects.common;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class LoseLifeOpponentsEffect extends OneShotEffect {

    private DynamicValue amount;

    public LoseLifeOpponentsEffect(int amount) {
        this(new StaticValue(amount));
    }

    public LoseLifeOpponentsEffect(DynamicValue amount) {
        super(Outcome.Damage);
        this.amount = amount;
        staticText = "each opponent loses " + amount + " life";
    }

    public LoseLifeOpponentsEffect(final LoseLifeOpponentsEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId: game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(opponentId);
            if (player != null) {
                player.loseLife(amount.calculate(game, source, this), game, false);
            }
        }
        return true;
    }

    @Override
    public LoseLifeOpponentsEffect copy() {
        return new LoseLifeOpponentsEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        String message = amount.getMessage();

        sb.append("each opponent loses ");
        if (message.isEmpty() || !message.equals("1")) {
            sb.append(amount).append(' ');
        }
        sb.append("life");
        if (!message.isEmpty()) {
            sb.append(message.equals("1") || message.startsWith("the ") ? " equal to the number of " : " for each ");
            sb.append(message);
        }
        return sb.toString();
    }
}
