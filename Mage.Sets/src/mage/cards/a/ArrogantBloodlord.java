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
package mage.cards.a;

import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class ArrogantBloodlord extends CardImpl {

    public ArrogantBloodlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Arrogant Bloodlord blocks or becomes blocked by a creature with power 1 or less, destroy Arrogant Bloodlord at end of combat.
        this.addAbility(new ArrogantBloodlordTriggeredAbility());
    }

    public ArrogantBloodlord(final ArrogantBloodlord card) {
        super(card);
    }

    @Override
    public ArrogantBloodlord copy() {
        return new ArrogantBloodlord(this);
    }
}

class ArrogantBloodlordTriggeredAbility extends TriggeredAbilityImpl {

    ArrogantBloodlordTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ArrogantBloodlordEffect());
    }

    ArrogantBloodlordTriggeredAbility(final ArrogantBloodlordTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ArrogantBloodlordTriggeredAbility copy() {
        return new ArrogantBloodlordTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent blocker = game.getPermanent(event.getSourceId());
        Permanent blocked = game.getPermanent(event.getTargetId());
        Permanent arrogantBloodlord = game.getPermanent(sourceId);
        if (blocker != null && !Objects.equals(blocker, arrogantBloodlord)
                && blocker.getPower().getValue() < 2
                && Objects.equals(blocked, arrogantBloodlord)) {
            return true;
        }
        return blocker != null && Objects.equals(blocker, arrogantBloodlord)
                && game.getPermanent(event.getTargetId()).getPower().getValue() < 2;
    }

    @Override
    public String getRule() {
        return "Whenever {this} blocks or becomes blocked by a creature with power 1 or less, destroy {this} at end of combat.";
    }
}

class ArrogantBloodlordEffect extends OneShotEffect {

    ArrogantBloodlordEffect() {
        super(Outcome.Detriment);
        staticText = "Destroy {this} at the end of combat";
    }

    ArrogantBloodlordEffect(final ArrogantBloodlordEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            AtTheEndOfCombatDelayedTriggeredAbility delayedAbility = new AtTheEndOfCombatDelayedTriggeredAbility(new DestroyTargetEffect());
            delayedAbility.getEffects().get(0).setTargetPointer(new FixedTarget(permanent, game));
            game.addDelayedTriggeredAbility(delayedAbility, source);
            return true;
        }
        return false;
    }

    @Override
    public ArrogantBloodlordEffect copy() {
        return new ArrogantBloodlordEffect(this);
    }
}
