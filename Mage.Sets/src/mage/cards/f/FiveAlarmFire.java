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
package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAnyTarget;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author Plopman
 */
public final class FiveAlarmFire extends CardImpl {

    public FiveAlarmFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}{R}");
        


        //Whenever a creature you control deals combat damage, put a blaze counter on Five-Alarm Fire.
        this.addAbility(new FiveAlarmFireTriggeredAbility());
        //Remove five blaze counters from Five-Alarm Fire: Five-Alarm Fire deals 5 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(5), new RemoveCountersSourceCost(CounterType.BLAZE.createInstance(5)));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    public FiveAlarmFire(final FiveAlarmFire card) {
        super(card);
    }

    @Override
    public FiveAlarmFire copy() {
        return new FiveAlarmFire(this);
    }
}


class FiveAlarmFireTriggeredAbility extends TriggeredAbilityImpl {

    // Because a creature that is blocked by multiple creatures it deals damage to, only causes to add one counter to ,
    // it's neccessary to remember which creature already triggered
    Set<UUID> triggeringCreatures = new HashSet<>();

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
    
    public FiveAlarmFireTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.BLAZE.createInstance()), false);
    }

    public FiveAlarmFireTriggeredAbility(final FiveAlarmFireTriggeredAbility ability) {
            super(ability);
            triggeringCreatures.addAll(ability.triggeringCreatures);
    }

    @Override
    public FiveAlarmFireTriggeredAbility copy() {
            return new FiveAlarmFireTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_CREATURE
                || event.getType() == EventType.DAMAGED_PLANESWALKER
                || event.getType() == EventType.DAMAGED_PLAYER
                || event.getType() == EventType.COMBAT_DAMAGE_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.DAMAGED_CREATURE
                || event.getType() == EventType.DAMAGED_PLANESWALKER
                || event.getType() == EventType.DAMAGED_PLAYER) {
            if (((DamagedEvent) event).isCombatDamage() && !triggeringCreatures.contains(event.getSourceId())) {
                Permanent permanent = game.getPermanent(event.getSourceId());
                if (permanent != null && filter.match(permanent, sourceId, controllerId, game)) {
                    triggeringCreatures.add(event.getSourceId());
                    return true;
                }
            }
        }
        // reset the remembered creatures for every combat damage step
        if (event.getType() == EventType.COMBAT_DAMAGE_STEP_PRE) {
            triggeringCreatures.clear();
        }
        return false;
    }

    @Override
    public String getRule() {
            return "Whenever a creature you control deals combat damage, " + super.getRule();
    }

}
