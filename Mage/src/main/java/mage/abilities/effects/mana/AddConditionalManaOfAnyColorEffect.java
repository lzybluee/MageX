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
package mage.abilities.effects.mana;

import mage.ConditionalMana;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.choices.ChoiceColor;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author noxx
 */
public class AddConditionalManaOfAnyColorEffect extends ManaEffect {

    private final DynamicValue amount;
    private final ConditionalManaBuilder manaBuilder;
    private final boolean oneChoice;

    public AddConditionalManaOfAnyColorEffect(int amount, ConditionalManaBuilder manaBuilder) {
        this(new StaticValue(amount), manaBuilder);
    }

    public AddConditionalManaOfAnyColorEffect(DynamicValue amount, ConditionalManaBuilder manaBuilder) {
        this(amount, manaBuilder, true);
    }

    public AddConditionalManaOfAnyColorEffect(DynamicValue amount, ConditionalManaBuilder manaBuilder, boolean oneChoice) {
        super();
        this.amount = amount;
        this.manaBuilder = manaBuilder;
        this.oneChoice = oneChoice;
        //
        staticText = "Add "
                + (amount instanceof StaticValue ? (CardUtil.numberToText(((StaticValue) amount).toString())) : "")
                + " mana "
                + (oneChoice || (amount instanceof StaticValue && (((StaticValue) amount).toString()).equals("1"))
                ? "of any" + (amount instanceof StaticValue && (((StaticValue) amount).toString()).equals("1") ? "" : " one") + " color"
                : "in any combination of colors")
                + ". " + manaBuilder.getRule();
    }

    public AddConditionalManaOfAnyColorEffect(final AddConditionalManaOfAnyColorEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.manaBuilder = effect.manaBuilder;
        this.oneChoice = effect.oneChoice;
    }

    @Override
    public AddConditionalManaOfAnyColorEffect copy() {
        return new AddConditionalManaOfAnyColorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            checkToFirePossibleEvents(getMana(game, source), game, source);
            controller.getManaPool().addMana(getMana(game, source), game, source);
            return true;
        }
        return false;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return null;
        }
        ConditionalMana mana = null;
        int value = amount.calculate(game, source, this);
        ChoiceColor choice = new ChoiceColor(true);
        for (int i = 0; i < value; i++) {
            if (choice.getChoice() == null) {
                controller.choose(outcome, choice, game);
            }
            if (choice.getChoice() == null) {
                return null;
            }
            if (oneChoice) {
                mana = new ConditionalMana(manaBuilder.setMana(choice.getMana(value), source, game).build());
                break;
            } else {
                if (mana == null) {
                    mana = new ConditionalMana(manaBuilder.setMana(choice.getMana(1), source, game).build());
                } else {
                    mana.add(choice.getMana(1));
                }
                choice.clearChoice();
            }
        }

        return mana;

    }
}
