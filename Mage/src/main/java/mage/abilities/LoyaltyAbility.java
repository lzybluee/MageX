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
package mage.abilities;

import mage.abilities.costs.common.PayLoyaltyCost;
import mage.abilities.costs.common.PayVariableLoyaltyCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.constants.TimingRule;
import mage.constants.Zone;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class LoyaltyAbility extends ActivatedAbilityImpl {

    public LoyaltyAbility(Effect effect, int loyalty) {
        super(Zone.BATTLEFIELD, effect, new PayLoyaltyCost(loyalty));
        this.timing = TimingRule.SORCERY;
    }

    public LoyaltyAbility(Effects effects, int loyalty) {
        super(Zone.BATTLEFIELD, effects, new PayLoyaltyCost(loyalty));
        this.timing = TimingRule.SORCERY;
    }

    public LoyaltyAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, new PayVariableLoyaltyCost());
        this.timing = TimingRule.SORCERY;
    }

    public LoyaltyAbility(Effects effects) {
        super(Zone.BATTLEFIELD, effects, new PayVariableLoyaltyCost());
        this.timing = TimingRule.SORCERY;
    }

    public LoyaltyAbility(LoyaltyAbility ability) {
        super(ability);
    }

    @Override
    public LoyaltyAbility copy() {
        return new LoyaltyAbility(this);
    }

}
