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
package mage.cards.v;

import java.util.UUID;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public class VampiresBite extends CardImpl {

    public VampiresBite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");


        // Kicker {2}{B} (You may pay an additional {2}{B} as you cast this spell.)
        this.addAbility(new KickerAbility("{2}{B}"));

        // Target creature gets +3/+0 until end of turn. If Vampire's Bite was kicked, that creature gains lifelink until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 0, Duration.EndOfTurn));
        ContinuousEffect effect = new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn);
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(effect, new LockedInCondition(KickedCondition.instance), "if this spell was kicked, that creature gains lifelink until end of turn"));
    }

    public VampiresBite(final VampiresBite card) {
        super(card);
    }

    @Override
    public VampiresBite copy() {
        return new VampiresBite(this);
    }
}
