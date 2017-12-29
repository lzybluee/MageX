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
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CounterUnlessPaysEffect extends OneShotEffect {

    protected Cost cost;
    protected DynamicValue genericMana;

    public CounterUnlessPaysEffect(Cost cost) {
        super(Outcome.Detriment);
        this.cost = cost;
    }

    public CounterUnlessPaysEffect(DynamicValue genericMana) {
        super(Outcome.Detriment);
        this.genericMana = genericMana;
    }

    public CounterUnlessPaysEffect(final CounterUnlessPaysEffect effect) {
        super(effect);
        if (effect.cost != null) {
            this.cost = effect.cost.copy();
        }
        if (effect.genericMana != null) {
            this.genericMana = effect.genericMana.copy();
        }
    }

    @Override
    public CounterUnlessPaysEffect copy() {
        return new CounterUnlessPaysEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject spell = game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (spell != null) {
            Player player = game.getPlayer(spell.getControllerId());
            if (player != null) {
                Cost costToPay;
                if (cost != null) {
                    costToPay = cost.copy();
                } else {
                    costToPay = new GenericManaCost(genericMana.calculate(game, source, this));
                }
                String message;
                if (costToPay instanceof ManaCost) {
                    message = "Would you like to pay " + costToPay.getText() + " to prevent counter effect?";
                } else {
                    message = costToPay.getText() + " to prevent counter effect?";
                }
                costToPay.clearPaid();
                if (!(player.chooseUse(Outcome.Benefit, message, source, game) && costToPay.pay(source, game, spell.getSourceId(), spell.getControllerId(), false, null))) {
                    game.informPlayers(player.getLogName() + " chooses not to pay " + costToPay.getText() + " to prevent the counter effect");
                    return game.getStack().counter(spell.getId(), source.getSourceId(), game);
                }
                game.informPlayers(player.getLogName() + " chooses to pay " + costToPay.getText() + " to prevent the counter effect");
                return true;
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }

        StringBuilder sb = new StringBuilder();
        if (mode.getTargets().isEmpty()) {
            sb.append("counter it");
        } else {
            sb.append("counter target ").append(mode.getTargets().get(0).getTargetName());
        }
        sb.append(" unless its controller pays ");
        if (cost != null) {
            sb.append(cost.getText());
        } else {
            sb.append("{X}");
        }
        if (genericMana != null && !genericMana.getMessage().isEmpty()) {
            sb.append(", where X is ");
            sb.append(genericMana.getMessage());
        }
        return sb.toString();
    }

}
