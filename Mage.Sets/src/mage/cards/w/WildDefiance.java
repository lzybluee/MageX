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
package mage.cards.w;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 * @author noxx
 */
public class WildDefiance extends CardImpl {

    public WildDefiance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}");

        // Whenever a creature you control becomes the target of an instant or sorcery spell, that creature gets +3/+3 until end of turn.
        this.addAbility(new CreaturesYouControlBecomesTargetTriggeredAbility(new BoostTargetEffect(3, 3, Duration.EndOfTurn)));
    }

    public WildDefiance(final WildDefiance card) {
        super(card);
    }

    @Override
    public WildDefiance copy() {
        return new WildDefiance(this);
    }
}

class CreaturesYouControlBecomesTargetTriggeredAbility extends TriggeredAbilityImpl {

    public CreaturesYouControlBecomesTargetTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    public CreaturesYouControlBecomesTargetTriggeredAbility(final CreaturesYouControlBecomesTargetTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CreaturesYouControlBecomesTargetTriggeredAbility copy() {
        return new CreaturesYouControlBecomesTargetTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && permanent.getControllerId().equals(this.controllerId) && permanent.isCreature()) {
            MageObject object = game.getObject(event.getSourceId());
            if (object != null && object instanceof Spell) {
                Card c = (Spell) object;
                if (c.isInstant() || c.isSorcery()) {
                    if (getTargets().isEmpty()) {
                        for (Effect effect : getEffects()) {
                            effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control becomes the target of an instant or sorcery spell, that creature gets +3/+3 until end of turn.";
    }
}
