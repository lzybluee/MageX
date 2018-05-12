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
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com AS IS AND ANY EXPRESS OR IMPLIED
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

package mage.game.permanent.token;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.constants.CardType;
import mage.abilities.keyword.ForestwalkAbility;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public class CatWarriorToken extends TokenImpl {

    final static private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("PLC", "C17"));
    }

    public CatWarriorToken() {
        super("Cat Warrior", "2/2 green Cat Warrior creature token with forestwalk");
        availableImageSetCodes = tokenImageSets;
        this.setOriginalExpansionSetCode("PLC");
        this.getPower().modifyBaseValue(2);
        this.getToughness().modifyBaseValue(2);
        this.color.setGreen(true);
        this.getSubtype(null).add(SubType.CAT);
        this.getSubtype(null).add(SubType.WARRIOR);
        this.addCardType(CardType.CREATURE);
        this.addAbility(new ForestwalkAbility());
    }

    public CatWarriorToken(final CatWarriorToken token) {
        super(token);
    }

    public CatWarriorToken copy() {
        return new CatWarriorToken(this);
    }
}
