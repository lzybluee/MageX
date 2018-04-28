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
package mage.abilities.costs.mana;

import java.util.ArrayList;
import java.util.List;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.constants.ColoredManaSymbol;
import mage.game.Game;
import mage.players.ManaPool;

public class MonoHybridManaCost extends ManaCostImpl {

    private final ColoredManaSymbol mana;
    private int mana2 = 2;

    public MonoHybridManaCost(ColoredManaSymbol mana) {
        this.mana = mana;
        this.cost = new Mana(mana);
        this.cost.add(Mana.GenericMana(2));
        addColoredOption(mana);
        options.add(Mana.GenericMana(2));
    }

    public MonoHybridManaCost(MonoHybridManaCost manaCost) {
        super(manaCost);
        this.mana = manaCost.mana;
        this.mana2 = manaCost.mana2;
    }

    @Override
    public int convertedManaCost() {
        return 2;
    }

    @Override
    public boolean isPaid() {
        if (paid || isColoredPaid(this.mana)) {
            return true;
        }
        return isColorlessPaid(this.mana2);
    }

    @Override
    public void assignPayment(Game game, Ability ability, ManaPool pool, Cost costToPay) {
        if (!assignColored(ability, game, pool, mana, costToPay)) {
            assignGeneric(ability, game, pool, mana2, costToPay);
        }
    }

    @Override
    public String getText() {
        return "{2/" + mana.toString() + '}';
    }

    @Override
    public MonoHybridManaCost getUnpaid() {
        return this;
    }

    @Override
    public boolean testPay(Mana testMana) {
        switch (mana) {
            case B:
                if (testMana.getBlack() > 0) {
                    return true;
                }
            case U:
                if (testMana.getBlue() > 0) {
                    return true;
                }
            case R:
                if (testMana.getRed() > 0) {
                    return true;
                }
            case W:
                if (testMana.getWhite() > 0) {
                    return true;
                }
            case G:
                if (testMana.getGreen() > 0) {
                    return true;
                }
        }
        return testMana.count() > 0;
    }

    @Override
    public MonoHybridManaCost copy() {
        return new MonoHybridManaCost(this);
    }

    @Override
    public boolean containsColor(ColoredManaSymbol coloredManaSymbol) {
        return mana == coloredManaSymbol;
    }

    public ColoredManaSymbol getManaColor() {
        return mana;
    }

    @Override
    public List<Mana> getManaOptions() {
        List<Mana> manaList = new ArrayList<>();
        manaList.add(new Mana(mana));
        manaList.add(Mana.GenericMana(2));
        return manaList;
    }
}
