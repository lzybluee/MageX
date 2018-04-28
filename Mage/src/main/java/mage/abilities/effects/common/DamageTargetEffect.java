/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.effects.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;

/**
 *
 * @author BetaSteward_at_googlemail.com
 * @author North
 */
public class DamageTargetEffect extends OneShotEffect {

    protected DynamicValue amount;
    protected boolean preventable;
    protected String targetDescription;
    protected boolean useOnlyTargetPointer;
    protected String sourceName = "{source}";

    public DamageTargetEffect(int amount) {
        this(new StaticValue(amount), true);
    }

    public DamageTargetEffect(int amount, String whoDealDamageName) {
        this(new StaticValue(amount), true);
        this.sourceName = whoDealDamageName;
    }

    public DamageTargetEffect(int amount, boolean preventable) {
        this(new StaticValue(amount), preventable);
    }

    public DamageTargetEffect(int amount, boolean preventable, String targetDescription) {
        this(new StaticValue(amount), preventable, targetDescription);
    }

    public DamageTargetEffect(int amount, boolean preventable, String targetDescription, String whoDealDamageName) {
        this(new StaticValue(amount), preventable, targetDescription);
        this.sourceName = whoDealDamageName;
    }

    public DamageTargetEffect(DynamicValue amount) {
        this(amount, true);
    }

    public DamageTargetEffect(DynamicValue amount, String whoDealDamageName) {
        this(amount, true);
        this.sourceName = whoDealDamageName;
    }

    public DamageTargetEffect(DynamicValue amount, boolean preventable) {
        this(amount, preventable, "");
    }

    public DamageTargetEffect(DynamicValue amount, boolean preventable, String targetDescription) {
        this(amount, preventable, targetDescription, false);
    }

    public DamageTargetEffect(DynamicValue amount, boolean preventable, String targetDescription, boolean useOnlyTargetPointer) {
        super(Outcome.Damage);
        this.amount = amount;
        this.preventable = preventable;
        this.targetDescription = targetDescription;
        this.useOnlyTargetPointer = useOnlyTargetPointer;
    }

    public int getAmount() {
        if (amount instanceof StaticValue) {
            return amount.calculate(null, null, this);
        } else {
            return 0;
        }
    }

    public void setAmount(DynamicValue amount) {
        this.amount = amount;
    }

    public DamageTargetEffect(final DamageTargetEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
        this.preventable = effect.preventable;
        this.targetDescription = effect.targetDescription;
        this.useOnlyTargetPointer = effect.useOnlyTargetPointer;
        this.sourceName = effect.sourceName;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Effect setUseOnlyTargetPointer(boolean useOnlyTargetPointer) {
        this.useOnlyTargetPointer = useOnlyTargetPointer;
        return this;
    }

    @Override
    public DamageTargetEffect copy() {
        return new DamageTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!useOnlyTargetPointer && source.getTargets().size() > 1) {
            for (Target target : source.getTargets()) {
                for (UUID targetId : target.getTargets()) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null) {
                        permanent.damage(amount.calculate(game, source, this), source.getSourceId(), game, false, preventable);
                    }
                    Player player = game.getPlayer(targetId);
                    if (player != null
                            && player.isInGame()) {
                        player.damage(amount.calculate(game, source, this), source.getSourceId(), game, false, preventable);
                    }
                }
            }
            return true;
        }
        for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                permanent.damage(amount.calculate(game, source, this), source.getSourceId(), game, false, preventable);
            } else {
                Player player = game.getPlayer(targetId);
                if (player != null
                        && player.isInGame()) {
                    player.damage(amount.calculate(game, source, this), source.getSourceId(), game, false, preventable);
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
        StringBuilder sb = new StringBuilder();
        String message = amount.getMessage();
        sb.append(this.sourceName).append(" deals ");
        if (message.isEmpty() || !message.equals("1")) {
            sb.append(amount);
        }
        sb.append(" damage to ");
        if (!targetDescription.isEmpty()) {
            sb.append(targetDescription);
        } else {
            String targetName = mode.getTargets().get(0).getTargetName();
            if (targetName.contains("any")) {
                sb.append(targetName);
            } else {
                sb.append("target ").append(targetName);
            }
        }
        if (!message.isEmpty()) {
            if (message.equals("1")) {
                sb.append(" equal to the number of ");
            } else {
                if (message.startsWith("the") || message.startsWith("twice")) {
                    sb.append(" equal to ");
                } else {
                    sb.append(" for each ");
                }
            }
            sb.append(message);
        }
        if (!preventable) {
            sb.append(". The damage can't be prevented");
        }
        return sb.toString();
    }
}
