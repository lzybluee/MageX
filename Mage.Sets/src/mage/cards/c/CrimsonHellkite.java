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
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterMana;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Quercitron
 */
public class CrimsonHellkite extends CardImpl {

    private static final FilterMana filterRedMana = new FilterMana();

    static {
        filterRedMana.setRed(true);
    }
    
    public CrimsonHellkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{R}{R}{R}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {X}, {tap}: Crimson Hellkite deals X damage to target creature. Spend only red mana on X.
        Effect effect = new DamageTargetEffect(new ManacostVariableValue());
        effect.setText("{this} deals X damage to target creature. Spend only red mana on X");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{X}"));
        ability.addCost(new TapSourceCost());
        VariableCost variableCost = ability.getManaCostsToPay().getVariableCosts().get(0);
        if (variableCost instanceof VariableManaCost) {
            ((VariableManaCost) variableCost).setFilter(filterRedMana);
        }
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public CrimsonHellkite(final CrimsonHellkite card) {
        super(card);
    }

    @Override
    public CrimsonHellkite copy() {
        return new CrimsonHellkite(this);
    }
}
