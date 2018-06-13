
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class HidetsugusSecondRite extends CardImpl {

    public HidetsugusSecondRite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{R}");


        // If target player has exactly 10 life, Hidetsugu's Second Rite deals 10 damage to that player.
        this.getSpellAbility().addEffect(new HidetsugusSecondRiteEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public HidetsugusSecondRite(final HidetsugusSecondRite card) {
        super(card);
    }

    @Override
    public HidetsugusSecondRite copy() {
        return new HidetsugusSecondRite(this);
    }
}

class HidetsugusSecondRiteEffect extends OneShotEffect {

    public HidetsugusSecondRiteEffect() {
        super(Outcome.Damage);
        this.staticText = "If target player has exactly 10 life, Hidetsugu's Second Rite deals 10 damage to that player";
    }

    public HidetsugusSecondRiteEffect(final HidetsugusSecondRiteEffect effect) {
        super(effect);
    }

    @Override
    public HidetsugusSecondRiteEffect copy() {
        return new HidetsugusSecondRiteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (targetPlayer != null) {
            if (targetPlayer.getLife() == 10) {
                targetPlayer.damage(10, source.getSourceId(), game, false, true);
            }
            return true;
        }
        return false;
    }
}
