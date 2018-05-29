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
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author LevelX2
 */
public final class GhostsOfTheInnocent extends CardImpl {

    public GhostsOfTheInnocent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // If a source would deal damage to a creature or player, it deals half that damage, rounded down, to that creature or player instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GhostsOfTheInnocentPreventDamageEffect()));
    }

    public GhostsOfTheInnocent(final GhostsOfTheInnocent card) {
        super(card);
    }

    @Override
    public GhostsOfTheInnocent copy() {
        return new GhostsOfTheInnocent(this);
    }
}

class GhostsOfTheInnocentPreventDamageEffect extends ReplacementEffectImpl implements PreventionEffect {

    public GhostsOfTheInnocentPreventDamageEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "If a source would deal damage to a permanent or player, it deals half that damage, rounded down, to that permanent or player instead";
    }

    public GhostsOfTheInnocentPreventDamageEffect(final GhostsOfTheInnocentPreventDamageEffect effect) {
        super(effect);
    }

    @Override
    public GhostsOfTheInnocentPreventDamageEffect copy() {
        return new GhostsOfTheInnocentPreventDamageEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGE_CREATURE
                || event.getType() == EventType.DAMAGE_PLAYER
                || event.getType() == EventType.DAMAGE_PLANESWALKER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int amount = (int) Math.ceil(event.getAmount() / 2.0);
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, event.getTargetId(), source.getSourceId(), source.getControllerId(), amount, false);
        if (!game.replaceEvent(preventEvent)) {
            event.setAmount(event.getAmount() - amount);
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, event.getTargetId(), source.getSourceId(), source.getControllerId(), amount));
        }
        return false;
    }
}
