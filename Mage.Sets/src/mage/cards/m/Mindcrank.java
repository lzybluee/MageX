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
package mage.cards.m;

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author North
 */
public final class Mindcrank extends CardImpl {

    public Mindcrank(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // Whenever an opponent loses life, that player puts that many cards from the top of their library into their graveyard.
        // (Damage dealt by sources without infect causes loss of life.)
        this.addAbility(new MindcrankTriggeredAbility());
    }

    public Mindcrank(final Mindcrank card) {
        super(card);
    }

    @Override
    public Mindcrank copy() {
        return new Mindcrank(this);
    }
}

class MindcrankTriggeredAbility extends TriggeredAbilityImpl {

    public MindcrankTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MindcrankEffect(), false);
    }

    public MindcrankTriggeredAbility(final MindcrankTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MindcrankTriggeredAbility copy() {
        return new MindcrankTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.LOST_LIFE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Set<UUID> opponents = game.getOpponents(this.getControllerId());
        if (opponents.contains(event.getPlayerId())) {
            for (Effect effect : this.getEffects()) {
                effect.setValue("lostLife", event.getAmount());
                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent loses life, that player puts that many cards from the top of their library into their graveyard.";
    }
}

class MindcrankEffect extends OneShotEffect {

    public MindcrankEffect() {
        super(Outcome.Detriment);
    }

    public MindcrankEffect(final MindcrankEffect effect) {
        super(effect);
    }

    @Override
    public MindcrankEffect copy() {
        return new MindcrankEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer != null) {
            Integer amount = (Integer) getValue("lostLife");
            if (amount == null) {
                amount = 0;
            }
            targetPlayer.moveCards(targetPlayer.getLibrary().getTopCards(game, amount), Zone.GRAVEYARD, source, game);
        }
        return true;
    }
}
