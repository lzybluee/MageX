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
package mage.cards.r;

import java.util.UUID;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.filter.common.FilterCreatureSpell;
import mage.game.permanent.token.KithkinToken;
import mage.target.TargetSpell;
import mage.watchers.common.ManaSpentToCastWatcher;

/**
 *
 * @author jeffwadsworth
 */
public final class RepelIntruders extends CardImpl {

    public RepelIntruders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{W/U}");


        // Create two 1/1 white Kithkin Soldier creature tokens if {W} was spent to cast Repel Intruders. Counter up to one target creature spell if {U} was spent to cast Repel Intruders.
        TargetSpell target = new TargetSpell(0,1, new FilterCreatureSpell());
        target.setRequired(false);
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CreateTokenEffect(new KithkinToken(), 2),
                new ManaWasSpentCondition(ColoredManaSymbol.W), "Create two 1/1 white Kithkin Soldier creature tokens if {W} was spent to cast {this}"));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CounterTargetEffect(),
                new ManaWasSpentCondition(ColoredManaSymbol.U), " Counter up to one target creature spell if {U} was spent to cast {this}"));
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addEffect(new InfoEffect("<i>(Do both if {W}{U} was spent.)</i>"));
        this.getSpellAbility().addWatcher(new ManaSpentToCastWatcher());
        
    }

    public RepelIntruders(final RepelIntruders card) {
        super(card);
    }

    @Override
    public RepelIntruders copy() {
        return new RepelIntruders(this);
    }
}

