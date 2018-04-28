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
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.util.RandomUtil;

/**
 *
 * @author spjspj
 */
public class OviyaPashiriSageLifecrafterToken extends TokenImpl {

    final static FilterControlledCreaturePermanent filterCreature = new FilterControlledCreaturePermanent("creatures you control");

    public OviyaPashiriSageLifecrafterToken() {
        this(1);
    }

    public OviyaPashiriSageLifecrafterToken(int number) {
        super("Construct", "an X/X colorless Construct artifact creature token, where X is the number of creatures you control");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.CONSTRUCT);
        setOriginalExpansionSetCode("KLD");
        setTokenType(RandomUtil.nextInt(2) + 1);
        power = new MageInt(number);
        toughness = new MageInt(number);
    }

    public OviyaPashiriSageLifecrafterToken(final OviyaPashiriSageLifecrafterToken token) {
        super(token);
    }

    public OviyaPashiriSageLifecrafterToken copy() {
        return new OviyaPashiriSageLifecrafterToken(this);
    }
}
