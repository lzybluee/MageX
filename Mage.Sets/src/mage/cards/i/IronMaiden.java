
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public final class IronMaiden extends CardImpl {

    public IronMaiden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // At the beginning of each opponent's upkeep, Iron Maiden deals X damage to that player, where X is the number of cards in their hand minus 4.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new IronMaidenEffect(), TargetController.OPPONENT, false);
        this.addAbility(ability);
    }

    public IronMaiden(final IronMaiden card) {
        super(card);
    }

    @Override
    public IronMaiden copy() {
        return new IronMaiden(this);
    }

}

class IronMaidenEffect extends OneShotEffect {

    private IronMaidenEffect(final IronMaidenEffect effect) {
        super(effect);
    }

    public IronMaidenEffect() {
        super(Outcome.Damage);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            int amount = player.getHand().size() - 4;
            if (amount > 0) {
                player.damage(amount, source.getSourceId(), game, false, true);
                return true;
            }
        }
        return false;
    }

    @Override
    public IronMaidenEffect copy() {
        return new IronMaidenEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        return "Iron Maiden deals X damage to that player, where X is the number of cards in their hand minus 4";
    }

}
