
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class GazeOfAdamaro extends CardImpl {

    public GazeOfAdamaro(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}{R}");
        this.subtype.add(SubType.ARCANE);


        // Gaze of Adamaro deals damage to target player equal to the number of cards in that player's hand.
        this.getSpellAbility().addEffect(new GazeOfAdamaroEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public GazeOfAdamaro(final GazeOfAdamaro card) {
        super(card);
    }

    @Override
    public GazeOfAdamaro copy() {
        return new GazeOfAdamaro(this);
    }
}

class GazeOfAdamaroEffect extends OneShotEffect {

    public GazeOfAdamaroEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals damage to target player equal to the number of cards in that player's hand";
    }

    public GazeOfAdamaroEffect(final GazeOfAdamaroEffect effect) {
        super(effect);
    }

    @Override
    public GazeOfAdamaroEffect copy() {
        return new GazeOfAdamaroEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer != null) {
            targetPlayer.damage(targetPlayer.getHand().size(), source.getSourceId(), game, false, true);
            return true;
        }
        return false;
    }
}
