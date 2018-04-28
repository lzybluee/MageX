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

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author spjspj
 */
public class SaprolingBurstToken extends TokenImpl {

    public SaprolingBurstToken() {
        this((MageObjectReference)null);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public SaprolingBurstToken(MageObjectReference saprolingBurstMOR) {
        super("Saproling", "green Saproling creature token with \"This creature's power and toughness are each equal to the number of fade counters on Saproling Burst.\"");
        this.color.setGreen(true);
        this.subtype.add(SubType.SAPROLING);
        this.cardType.add(CardType.CREATURE);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SetPowerToughnessSourceEffect(new SaprolingBurstTokenDynamicValue(saprolingBurstMOR), Duration.WhileOnBattlefield)));
    }

    public SaprolingBurstToken(final SaprolingBurstToken token) {
        super(token);
    }

    public SaprolingBurstToken copy() {
        return new SaprolingBurstToken(this);
    }
}

class SaprolingBurstTokenDynamicValue implements DynamicValue {

    private final MageObjectReference saprolingBurstMOR;

    SaprolingBurstTokenDynamicValue(MageObjectReference saprolingBurstMOR) {
        this.saprolingBurstMOR = saprolingBurstMOR;
    }

    SaprolingBurstTokenDynamicValue(final SaprolingBurstTokenDynamicValue dynamicValue) {
        this.saprolingBurstMOR = dynamicValue.saprolingBurstMOR;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent permanent = this.saprolingBurstMOR.getPermanent(game);
        if (permanent != null) {
            return permanent.getCounters(game).getCount(CounterType.FADE);
        }
        return 0;
    }

    @Override
    public SaprolingBurstTokenDynamicValue copy() {
        return new SaprolingBurstTokenDynamicValue(this);
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "the number of fade counters on Saproling Burst";
    }
}
