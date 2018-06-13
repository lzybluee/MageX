
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author North
 */
public final class MoltenRain extends CardImpl {

    public MoltenRain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}{R}");


        // Destroy target land.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetLandPermanent());
        // If that land was nonbasic, Molten Rain deals 2 damage to the land's controller.
        this.getSpellAbility().addEffect(new MoltenRainEffect());
    }

    public MoltenRain(final MoltenRain card) {
        super(card);
    }

    @Override
    public MoltenRain copy() {
        return new MoltenRain(this);
    }
}

class MoltenRainEffect extends OneShotEffect {

    public MoltenRainEffect() {
        super(Outcome.Damage);
        this.staticText = "If that land was nonbasic, Molten Rain deals 2 damage to the land's controller";
    }

    public MoltenRainEffect(final MoltenRainEffect effect) {
        super(effect);
    }

    @Override
    public MoltenRainEffect copy() {
        return new MoltenRainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) game.getLastKnownInformation(source.getFirstTarget(), Zone.BATTLEFIELD);
        if (permanent != null && !permanent.isBasic()) {
            Player player = game.getPlayer(permanent.getControllerId());
            if (player != null) {
                player.damage(2, source.getSourceId(), game, false, true);
                return true;
            }
        }
        return false;
    }
}
