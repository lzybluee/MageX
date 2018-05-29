/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.target.Target;
import mage.target.common.TargetAnyTarget;
import mage.util.CardUtil;

/**
 *
 * @author LoneFox
 *
 */
public final class KaerveksTorch extends CardImpl {

    public KaerveksTorch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{R}");

        // As long as Kaervek's Torch is on the stack, spells that target it cost {2} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new KaerveksTorchCostIncreaseEffect()));
        // Kaervek's Torch deals X damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(new ManacostVariableValue()));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    public KaerveksTorch(final KaerveksTorch card) {
        super(card);
    }

    @Override
    public KaerveksTorch copy() {
        return new KaerveksTorch(this);
    }
}

class KaerveksTorchCostIncreaseEffect extends CostModificationEffectImpl {

    KaerveksTorchCostIncreaseEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = "Spells that target {this} cost {2} more to cast";
    }

    KaerveksTorchCostIncreaseEffect(KaerveksTorchCostIncreaseEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.increaseCost(abilityToModify, 2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            for (UUID modeId : abilityToModify.getModes().getSelectedModes()) {
                Mode mode = abilityToModify.getModes().get(modeId);
                for (Target target : mode.getTargets()) {
                    for (UUID targetId : target.getTargets()) {
                        if (targetId.equals(source.getSourceObject(game).getId())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public KaerveksTorchCostIncreaseEffect copy() {
        return new KaerveksTorchCostIncreaseEffect(this);
    }
}
