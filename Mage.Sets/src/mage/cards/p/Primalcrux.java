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
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class Primalcrux extends CardImpl {

    public Primalcrux(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{G}{G}{G}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Chroma - Primalcrux's power and toughness are each equal to the number of green mana symbols in the mana costs of permanents you control.
        Effect effect = new SetPowerToughnessSourceEffect(new ChromaPrimalcruxCount(), Duration.WhileOnBattlefield);
        effect.setText("<i>Chroma</i> &mdash; Primalcrux's power and toughness are each equal to the number of green mana symbols in the mana costs of permanents you control.");
        this.addAbility(new SimpleStaticAbility(Zone.ALL, effect));
    }

    public Primalcrux(final Primalcrux card) {
        super(card);
    }

    @Override
    public Primalcrux copy() {
        return new Primalcrux(this);
    }
}

class ChromaPrimalcruxCount implements DynamicValue {

    private int chroma;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        chroma = 0;
        for (Card card : game.getBattlefield().getAllActivePermanents(new FilterControlledPermanent(), sourceAbility.getControllerId(), game)) {
            chroma += card.getManaCost().getMana().getGreen();
        }
        return chroma;
    }

    @Override
    public DynamicValue copy() {
        return new ChromaPrimalcruxCount();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "";
    }
}
