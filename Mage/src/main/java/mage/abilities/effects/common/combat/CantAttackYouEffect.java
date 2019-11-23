package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class CantAttackYouEffect extends RestrictionEffect {
    
    private boolean ifAble = false;

    public CantAttackYouEffect(Duration duration) {
        super(duration);
    }

    public CantAttackYouEffect(final CantAttackYouEffect effect) {
        super(effect);
    }

    public CantAttackYouEffect(final CantAttackYouEffect effect, boolean able) {
        super(effect);
        ifAble = able;
    }

    @Override
    public CantAttackYouEffect copy() {
        return new CantAttackYouEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return this.getTargetPointer().getTargets(game, source).contains(permanent.getId());
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        if (defenderId == null) {
            return true;
        }
        if(ifAble) {
            if(game.getCombat() != null && game.getCombat().getDefenders() != null) {
                if(game.getCombat().getDefenders().size() == 1) {
                    for (UUID playerId : game.getCombat().getDefenders()) {
                        if(defenderId.equals(playerId)) {
                            return true;
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        return !defenderId.equals(source.getControllerId());
    }
}
