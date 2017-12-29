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

package mage.abilities.effects;

import mage.abilities.Ability;
import mage.game.Game;

/**
 * Represents a {@link ContinuousEffect} that will modify the cost and abilities of a spell
 * on the stack (splice a card).  {@link mage.abilities.Ability Abilities} with this type of effect will be
 * called once and only once from the {@link mage.abilities.AbilityImpl#activate(mage.game.Game,
 * boolean) Ability.activate} method before alternative or additional costs are paid.
 *
 * @author levelX2
 */

public interface SpliceCardEffect extends ContinuousEffect {
    /**
     * Called by the {@link ContinuousEffects#costModification(Ability abilityToModify, Game game) ContinuousEffects.costModification}
     * method.
     *
     * @param game The game for which this effect should be applied.
     * @param source The source ability of this effect.
     * @param abilityToModify The {@link mage.abilities.SpellAbility} or {@link Ability} which should be modified.
     * @return
     */
    boolean apply ( Game game, Ability source, Ability abilityToModify );

    /**
     * Called by the {@link ContinuousEffects#costModification(mage.abilities.Ability, mage.game.Game) ContinuousEffects.costModification}
     * method.
     *
     * @param abilityToModify The ability to possibly modify.
     * @param source The source ability of this effect.
     * @param game The game for which this effect shoul dbe applied.
     * @return
     */
    boolean applies(Ability abilityToModify, Ability source, Game game);
}
