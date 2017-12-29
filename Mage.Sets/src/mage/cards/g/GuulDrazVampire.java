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
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.XorLessLifeCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author North
 */
public class GuulDrazVampire extends CardImpl {

    private static final String rule1 = "As long as an opponent has 10 or less life, {this} gets +2/+1";
    private static final String rule2 = "and has intimidate. <i>(It can't be blocked except by artifact creatures and/or creatures that share a color with it.)</i>";

    public GuulDrazVampire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ROGUE);

        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // As long as an opponent has 10 or less life, Guul Draz Vampire gets +2/+1 and has intimidate. (It can't be blocked except by artifact creatures and/or creatures that share a color with it.)
        Condition condition = new XorLessLifeCondition(XorLessLifeCondition.CheckType.AN_OPPONENT, 10);
        ConditionalContinuousEffect effect1 = new ConditionalContinuousEffect(new BoostSourceEffect(2, 1, Duration.WhileOnBattlefield), condition, rule1);
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect1);
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(IntimidateAbility.getInstance()), condition, rule2));
        this.addAbility(ability);

    }

    public GuulDrazVampire(final GuulDrazVampire card) {
        super(card);
    }

    @Override
    public GuulDrazVampire copy() {
        return new GuulDrazVampire(this);
    }
}
