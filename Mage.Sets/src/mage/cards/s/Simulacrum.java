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
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.watchers.common.AmountOfDamageAPlayerReceivedThisTurnWatcher;

/**
 *
 * @author MTGfan
 */
public final class Simulacrum extends CardImpl {

    public Simulacrum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");
        

        // You gain life equal to the damage dealt to you this turn. Simulacrum deals damage to target creature you control equal to the damage dealt to you this turn.
        this.getSpellAbility().addEffect(new GainLifeEffect(new SimulacrumAmount(), "You gain life equal to the damage dealt to you this turn."));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        Effect effect = new DamageTargetEffect(new SimulacrumAmount(), true, "target creature you control", true);
        effect.setText(" {this} deals damage to target creature you control equal to the damage dealt to you this turn.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addWatcher(new AmountOfDamageAPlayerReceivedThisTurnWatcher());
    }

    public Simulacrum(final Simulacrum card) {
        super(card);
    }

    @Override
    public Simulacrum copy() {
        return new Simulacrum(this);
    }
}

class SimulacrumAmount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        AmountOfDamageAPlayerReceivedThisTurnWatcher watcher = (AmountOfDamageAPlayerReceivedThisTurnWatcher) game.getState().getWatchers().get(AmountOfDamageAPlayerReceivedThisTurnWatcher.class.getSimpleName());
        if(watcher != null) {
            return watcher.getAmountOfDamageReceivedThisTurn(source.getControllerId());
        }
        return 0;
    }

    @Override
    public SimulacrumAmount copy() {
        return new SimulacrumAmount();
    }

    @Override
    public String getMessage() {
        return "";
    }
}
