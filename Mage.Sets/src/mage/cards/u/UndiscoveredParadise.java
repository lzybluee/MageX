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
package mage.cards.u;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class UndiscoveredParadise extends CardImpl {

    public UndiscoveredParadise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add one mana of any color. During your next untap step, as you untap your permanents, return Undiscovered Paradise to its owner's hand.
        Ability ability = new AnyColorManaAbility();
        ability.addEffect(new UndiscoveredParadiseEffect());
        this.addAbility(ability);
    }

    public UndiscoveredParadise(final UndiscoveredParadise card) {
        super(card);
    }

    @Override
    public UndiscoveredParadise copy() {
        return new UndiscoveredParadise(this);
    }
}

class UndiscoveredParadiseEffect extends OneShotEffect {

    public UndiscoveredParadiseEffect() {
        super(Outcome.Neutral);
        staticText = "During your next untap step, as you untap your permanents, return {this} to its owner's hand";
    }

    public UndiscoveredParadiseEffect(final UndiscoveredParadiseEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            Effect effect = new ReturnToHandTargetEffect();
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addDelayedTriggeredAbility(new AtBeginningOfUntapDelayedTriggeredAbility(effect), source);
            return true;
        }
        return false;
    }

    @Override
    public UndiscoveredParadiseEffect copy() {
        return new UndiscoveredParadiseEffect(this);
    }
}

class AtBeginningOfUntapDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public AtBeginningOfUntapDelayedTriggeredAbility(Effect effect) {
        super(effect);
        this.usesStack = false;
    }

    public AtBeginningOfUntapDelayedTriggeredAbility(AtBeginningOfUntapDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getActivePlayerId().equals(controllerId)) {
            return true;
        }
        return false;
    }

    @Override
    public AtBeginningOfUntapDelayedTriggeredAbility copy() {
        return new AtBeginningOfUntapDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Return {this} to its owner's hand.";
    }
}
