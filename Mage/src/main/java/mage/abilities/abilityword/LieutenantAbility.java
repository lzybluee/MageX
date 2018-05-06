/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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

package mage.abilities.abilityword;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CommanderInPlayCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author emerald000
 */

public class LieutenantAbility extends SimpleStaticAbility {

    public LieutenantAbility(ContinuousEffect effect) {
        super(Zone.BATTLEFIELD, new ConditionalContinuousEffect(new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield), CommanderInPlayCondition.instance, "<i>Lieutenant</i> &mdash; As long as you control your commander, {this} gets +2/+2"));
        this.addEffect(new ConditionalContinuousEffect(effect, CommanderInPlayCondition.instance, effect.getText(null)));
    }
    
    public LieutenantAbility(Effects effects) {
        super(Zone.BATTLEFIELD, new ConditionalContinuousEffect(new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield), CommanderInPlayCondition.instance, "<i>Lieutenant</i> &mdash; As long as you control your commander, {this} gets +2/+2"));
        for (Effect effect : effects) {
            this.addEffect(new ConditionalContinuousEffect((ContinuousEffect) effect, CommanderInPlayCondition.instance, effect.getText(null)));
        }
    }

    public LieutenantAbility(final LieutenantAbility ability) {
        super(ability);
    }

    @Override
    public LieutenantAbility copy() {
        return new LieutenantAbility(this);
    }
}