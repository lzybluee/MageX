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
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessAllEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author Plopman
 */
public class MirrorEntity extends CardImpl {

    static private FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Creatures you control");

    public MirrorEntity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Changeling
        this.addAbility(ChangelingAbility.getInstance());
        // {X}: Until end of turn, creatures you control have base power and toughness X/X and gain all creature types.
        DynamicValue variableMana = new ManacostVariableValue();
        Effect effect = new SetPowerToughnessAllEffect(variableMana, variableMana, Duration.EndOfTurn, filter, true);
        effect.setText("Until end of turn, creatures you control have base power and toughness X/X");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new VariableManaCost());
        effect = new GainAbilityAllEffect(ChangelingAbility.getInstance(), Duration.EndOfTurn, filter, false, Layer.TypeChangingEffects_4, SubLayer.NA);
        effect.setText("and gain all creature types");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public MirrorEntity(final MirrorEntity card) {
        super(card);
    }

    @Override
    public MirrorEntity copy() {
        return new MirrorEntity(this);
    }
}
