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
package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.cards.Card;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.util.CardUtil;

/**
 *
 * @author Quercitron
 */
public class SpellsCostIncreasementControllerEffect extends CostModificationEffectImpl {

    private final FilterCard filter;
    private final int amount;
    private ManaCosts<ManaCost> manaCostsToIncrease = null;

    public SpellsCostIncreasementControllerEffect(FilterCard filter, ManaCosts<ManaCost> manaCostsToReduce) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, CostModificationType.INCREASE_COST);
        this.filter = filter;
        this.amount = 0;
        this.manaCostsToIncrease = manaCostsToReduce;

        StringBuilder sb = new StringBuilder();
        sb.append(filter.getMessage()).append(" you cast cost ");
        for (String manaSymbol : manaCostsToReduce.getSymbols()) {
            sb.append(manaSymbol);
        }
        sb.append(" more to cast");
        this.staticText = sb.toString();
    }

    public SpellsCostIncreasementControllerEffect(FilterCard filter, int amount) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, CostModificationType.INCREASE_COST);
        this.filter = filter;
        this.amount = amount;

        StringBuilder sb = new StringBuilder();
        sb.append(filter.getMessage()).append(" you cast cost {").append(amount).append("} more to cast");
        this.staticText = sb.toString();
    }

    protected SpellsCostIncreasementControllerEffect(SpellsCostIncreasementControllerEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.amount = effect.amount;
        this.manaCostsToIncrease = effect.manaCostsToIncrease;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        if (manaCostsToIncrease != null) {
            CardUtil.increaseCost((SpellAbility) abilityToModify, manaCostsToIncrease);
        } else {
            CardUtil.increaseCost(abilityToModify, this.amount);
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            if (abilityToModify.getControllerId().equals(source.getControllerId())) {
                Spell spell = (Spell) game.getStack().getStackObject(abilityToModify.getId());
                if (spell != null) {
                    return this.filter.match(spell, game);
                } else {
                    // used at least for flashback ability because Flashback ability doesn't use stack
                    Card sourceCard = game.getCard(abilityToModify.getSourceId());
                    return sourceCard != null && this.filter.match(sourceCard, game);
                }
            }
        }
        return false;
    }

    @Override
    public SpellsCostIncreasementControllerEffect copy() {
        return new SpellsCostIncreasementControllerEffect(this);
    }
}
