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
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author nantuko
 */
public final class CircleOfFlame extends CardImpl {

    public CircleOfFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}");


        // Whenever a creature without flying attacks you or a planeswalker you control, Circle of Flame deals 1 damage to that creature.
        this.addAbility(new CircleOfFlameTriggeredAbility());
    }

    public CircleOfFlame(final CircleOfFlame card) {
        super(card);
    }

    @Override
    public CircleOfFlame copy() {
        return new CircleOfFlame(this);
    }
}

class CircleOfFlameTriggeredAbility extends TriggeredAbilityImpl {

    public CircleOfFlameTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(1));
    }

    public CircleOfFlameTriggeredAbility(final CircleOfFlameTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CircleOfFlameTriggeredAbility copy() {
        return new CircleOfFlameTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // check has flying
        Permanent attacker = game.getPermanent(event.getSourceId());
        if (attacker == null || attacker.getAbilities().contains(FlyingAbility.getInstance())) {
            return false;
        }

        // check attacks you
        boolean youOrYourPlaneswalker;
        boolean you = event.getTargetId().equals(this.getControllerId());
        if (you) {
            youOrYourPlaneswalker = true;
        } else{ // check attacks your planeswalker
            Permanent permanent = game.getPermanent(event.getTargetId());
            youOrYourPlaneswalker = permanent != null
                    && permanent.isPlaneswalker()
                    && permanent.getControllerId().equals(this.getControllerId());
        }
        if (youOrYourPlaneswalker) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(attacker.getId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature without flying attacks you or a planeswalker you control, Circle of Flame deals 1 damage to that creature.";
    }
}
