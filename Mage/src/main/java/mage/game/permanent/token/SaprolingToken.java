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

package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.util.RandomUtil;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SaprolingToken extends TokenImpl {

    final static private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList(
                "10E",
                "ALA",
                "DDE",
                "DDH",
                "DDJ",
                "M12",
                "M13",
                "M14",
                "MM2",
                "MM3",
                "MMA",
                "RTR",
                "C15",
                "MM3",
                "C16", // 2 different token images...
                "CMA",
                "VMA", // 2 different token, one with DIFFERENT stats, "Saproling Burst" create different token, see https://scryfall.com/card/tvma/12
                "E02",
                "RIX",
                "DOM" // 3 different token images
        ));
    }

    public SaprolingToken() {
        this(null, 0);
    }

    public SaprolingToken(String setCode) {
        this(setCode, 0);
    }

    public SaprolingToken(String setCode, int tokenType) {
        super("Saproling", "1/1 green Saproling creature token");
        availableImageSetCodes = tokenImageSets;
        setOriginalExpansionSetCode(setCode);
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("C16")) {
            this.setTokenType(RandomUtil.nextInt(2) + 1);
        }
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("DOM")) {
            this.setTokenType(RandomUtil.nextInt(3) + 1);
        }
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.SAPROLING);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public SaprolingToken(final SaprolingToken token) {
        super(token);
    }

    public SaprolingToken copy() {
        return new SaprolingToken(this);
    }
}