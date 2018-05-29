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
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.abilityword.LieutenantAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class ThunderfootBaloth extends CardImpl {

    public ThunderfootBaloth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // Lieutenant - As long as you control your commander, Thunderfoot Baloth gets +2/+2 and other creatures you control get +2/+2 and have trample.
        Effects effects = new Effects();
        Effect effect = new BoostControlledEffect(2, 2, Duration.WhileOnBattlefield, true);
        effect.setText("and other creatures you control get +2/+2");
        effects.add(effect);
        effect = new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield, new FilterControlledCreaturePermanent(), true);
        effect.setText("and have trample");
        effects.add(effect);
        this.addAbility(new LieutenantAbility(effects));
    }

    public ThunderfootBaloth(final ThunderfootBaloth card) {
        super(card);
    }

    @Override
    public ThunderfootBaloth copy() {
        return new ThunderfootBaloth(this);
    }
}
