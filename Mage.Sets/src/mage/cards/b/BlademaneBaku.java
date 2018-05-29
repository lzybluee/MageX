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

package mage.cards.b;

 import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
 import mage.cards.CardSetInfo;
 import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;

/**
 * @author LevelX2
 */
public final class BlademaneBaku extends CardImpl {

    public BlademaneBaku(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        
        // Whenever you cast a Spirit or Arcane spell, you may put a ki counter on Skullmane Baku.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.KI.createInstance()), StaticFilters.SPIRIT_OR_ARCANE_CARD, true));

        // {1}, Remove X ki counters from Blademane Baku: For each counter removed, Blademane Baku gets +2/+0 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BlademaneBakuBoostEffect(), new GenericManaCost(1));
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.KI.createInstance(1)));
        this.addAbility(ability);
    }

    public BlademaneBaku(final BlademaneBaku card) {
        super(card);
    }

    @Override
    public BlademaneBaku copy() {
        return new BlademaneBaku(this);
    }
    
    static class BlademaneBakuBoostEffect extends OneShotEffect {

        public BlademaneBakuBoostEffect() {
            super(Outcome.UnboostCreature);
            staticText = "For each counter removed, {this} gets +2/+0 until end of turn";
        }

        public BlademaneBakuBoostEffect(BlademaneBakuBoostEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            int numberToBoost = 0;
            for (Cost cost : source.getCosts()) {
                if (cost instanceof RemoveVariableCountersSourceCost) {
                    numberToBoost = ((RemoveVariableCountersSourceCost)cost).getAmount() * 2;
                }
            }
            if (numberToBoost >= 0) {
                game.addEffect(new BoostSourceEffect(numberToBoost, 0, Duration.EndOfTurn), source);
                return true;
            }
            return false;
        }

        @Override
        public BlademaneBakuBoostEffect copy() {
            return new BlademaneBakuBoostEffect(this);
        }

    }
}