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
package mage.abilities.effects.common.cost;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CastCommanderAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;

/**
 *
 * @author Plopman
 */
//20130711
/*903.10. A player may cast a commander he or she owns from the command zone.
 * Doing so costs that player an additional {2} for each previous time he or she cast that commander from the command zone that game.
 * */
public class CommanderCostModification extends CostModificationEffectImpl {

    private final UUID commanderId;

    public CommanderCostModification(UUID commanderId) {
        super(Duration.Custom, Outcome.Neutral, CostModificationType.INCREASE_COST);
        this.commanderId = commanderId;
    }

    public CommanderCostModification(final CommanderCostModification effect) {
        super(effect);
        this.commanderId = effect.commanderId;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Integer castCount = (Integer) game.getState().getValue(commanderId + "_castCount");
        if (castCount > 0) {
            abilityToModify.getManaCostsToPay().add(new GenericManaCost(2 * castCount));
        }
        return true;

    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof CastCommanderAbility && abilityToModify.getSourceId().equals(commanderId);
    }

    @Override
    public CommanderCostModification copy() {
        return new CommanderCostModification(this);
    }
}
