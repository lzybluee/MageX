
package mage.abilities.effects.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DamageMultiEffect extends OneShotEffect {

    protected DynamicValue amount;
    private String sourceName = "{source}";

    public DamageMultiEffect(int amount) {
        this(new StaticValue(amount));
    }

    public DamageMultiEffect(int amount, String whoDealDamageName) {
        this(new StaticValue(amount));
        this.sourceName = whoDealDamageName;
    }

    public DamageMultiEffect(DynamicValue amount) {
        super(Outcome.Damage);
        this.amount = amount;
    }

    public DamageMultiEffect(final DamageMultiEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.sourceName = effect.sourceName;
    }

    @Override
    public DamageMultiEffect copy() {
        return new DamageMultiEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!source.getTargets().isEmpty()) {
            Target multiTarget = source.getTargets().get(0);
            for (UUID target : multiTarget.getTargets()) {
                Permanent permanent = game.getPermanent(target);
                if (permanent != null) {
                    permanent.damage(multiTarget.getTargetAmount(target), source.getSourceId(), game, false, true);
                } else {
                    Player player = game.getPlayer(target);
                    if (player != null) {
                        player.damage(multiTarget.getTargetAmount(target), source.getSourceId(), game, false, true);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return this.sourceName + " deals " + amount.toString() + " damage divided as you choose among any number of " + mode.getTargets().get(0).getTargetName();
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}
