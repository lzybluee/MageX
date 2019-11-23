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
public class CantAttackYouIfAbleEffect extends RestrictionEffect {

    public CantAttackYouIfAbleEffect(Duration duration) {
        super(duration);
    }
    
    public CantAttackYouIfAbleEffect(final CantAttackYouIfAbleEffect effect) {
        super(effect);
    }

    @Override
    public CantAttackYouIfAbleEffect copy() {
        return new CantAttackYouIfAbleEffect(this);
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
        return !defenderId.equals(source.getControllerId());
    }
}
