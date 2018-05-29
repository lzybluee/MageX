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

import java.util.Objects;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public final class SpiritualFocus extends CardImpl {

    public SpiritualFocus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // Whenever a spell or ability an opponent controls causes you to discard a card, you gain 2 life and you may draw a card.
        this.addAbility(new SpiritualFocusTriggeredAbility());
    }

    public SpiritualFocus(final SpiritualFocus card) {
        super(card);
    }

    @Override
    public SpiritualFocus copy() {
        return new SpiritualFocus(this);
    }
}

class SpiritualFocusTriggeredAbility extends TriggeredAbilityImpl {

    public SpiritualFocusTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(2), false);
        this.addEffect(new SpiritualFocusDrawCardEffect());
    }

    public SpiritualFocusTriggeredAbility(final SpiritualFocusTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SpiritualFocusTriggeredAbility copy() {
        return new SpiritualFocusTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DISCARDED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
        if (stackObject != null) {
            if (game.getOpponents(this.getControllerId()).contains(stackObject.getControllerId())) {
                Permanent permanent = game.getPermanent(getSourceId());
                if (permanent != null) {
                    if (Objects.equals(permanent.getControllerId(), event.getPlayerId())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a spell or ability an opponent controls causes you to discard a card, you gain 2 life and you may draw a card.";
    }
}

class SpiritualFocusDrawCardEffect extends OneShotEffect {

    public SpiritualFocusDrawCardEffect() {
        super(Outcome.DrawCard);
    }

    public SpiritualFocusDrawCardEffect(final SpiritualFocusDrawCardEffect effect) {
        super(effect);
    }

    @Override
    public SpiritualFocusDrawCardEffect copy() {
        return new SpiritualFocusDrawCardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && permanent != null) {
            if (player.chooseUse(outcome, "Draw a card (" + permanent.getLogName() + ')', source, game)) {
                player.drawCards(1, game);
            }
            return true;
        }
        return false;
    }
}
