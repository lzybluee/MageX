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
package mage.choices;

import mage.MageObject;
import mage.Mana;
import mage.ObjectColor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ChoiceColor extends ChoiceImpl {

    //public static final Set<String> colorChoices =  new HashSet<>(); // JayDi85: uncomment 1 of 2 to broke unit tests find wrong tests (?)
    public static final ArrayList<String> colorChoices =  new ArrayList<>();

    static {
        colorChoices.add("Green");
        colorChoices.add("Blue");
        colorChoices.add("Black");
        colorChoices.add("Red");
        colorChoices.add("White");
    }

    public ChoiceColor() {
        this(true);
    }

    public ChoiceColor(boolean required) {
        this(required, "Choose color");
    }

    public ChoiceColor(boolean required, String chooseMessage){
        this(required, chooseMessage, "");
    }

    public ChoiceColor(boolean required, String chooseMessage, MageObject source){
        this(required, chooseMessage, source.getIdName());
    }

    public ChoiceColor(boolean required, String chooseMessage, String chooseSubMessage){
        super(required);

        this.choices.addAll(colorChoices);
        //this.setChoices(colorChoices); // JayDi85: uncomment 2 of 2 to broke unit tests find wrong tests (?)

        this.setMessage(chooseMessage);
        this.setSubMessage(chooseSubMessage);
    }

    public ChoiceColor(final ChoiceColor choice) {
        super(choice);
    }

    @Override
    public ChoiceColor copy() {
        return new ChoiceColor(this);
    }

    public ObjectColor getColor() {
        if (choice == null) {
            return null;
        }
        ObjectColor color = new ObjectColor();
        switch (choice) {
            case "Black":
                color.setBlack(true);
                break;
            case "Blue":
                color.setBlue(true);
                break;
            case "Green":
                color.setGreen(true);
                break;
            case "Red":
                color.setRed(true);
                break;
            case "White":
                color.setWhite(true);
                break;
        }
        return color;
    }

    public Mana getMana(int amount) {
        Mana mana;
        if (getColor().isBlack()) {
            mana = Mana.BlackMana(amount);
        } else if (getColor().isBlue()) {
            mana = Mana.BlueMana(amount);
        } else if (getColor().isRed()) {
            mana = Mana.RedMana(amount);
        } else if (getColor().isGreen()) {
            mana = Mana.GreenMana(amount);
        } else if (getColor().isWhite()) {
            mana = Mana.WhiteMana(amount);
        } else {
            mana = Mana.ColorlessMana(amount);
        }
        return mana;
    }

    public void increaseMana(Mana mana) {
        if (getColor().isBlack()) {
            mana.increaseBlack();
        } else if (getColor().isBlue()) {
            mana.increaseBlue();
        } else if (getColor().isRed()) {
            mana.increaseRed();
        } else if (getColor().isGreen()) {
            mana.increaseGreen();
        } else if (getColor().isWhite()) {
            mana.increaseWhite();
        } else {
            mana.increaseColorless();
        }
    }
}
