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
package mage.watchers.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

/**
 * @author jeffwadsworth
 *
 *         Amount of damage received by a player this turn
 */
public class AmountOfDamageAPlayerReceivedThisTurnWatcher extends Watcher {

    private final Map<UUID, Integer> amountOfDamageReceivedThisTurn = new HashMap<>();

    public AmountOfDamageAPlayerReceivedThisTurnWatcher() {
        super(AmountOfDamageAPlayerReceivedThisTurnWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public AmountOfDamageAPlayerReceivedThisTurnWatcher(final AmountOfDamageAPlayerReceivedThisTurnWatcher watcher) {
        super(watcher);
        for (Entry<UUID, Integer> entry : watcher.amountOfDamageReceivedThisTurn.entrySet()) {
            amountOfDamageReceivedThisTurn.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
            UUID playerId = event.getTargetId();
            if (playerId != null) {
                amountOfDamageReceivedThisTurn.putIfAbsent(playerId, 0);
                amountOfDamageReceivedThisTurn.compute(playerId, (k, v) -> v + event.getAmount());
            }
        }
    }

    public int getAmountOfDamageReceivedThisTurn(UUID playerId) {
        return amountOfDamageReceivedThisTurn.getOrDefault(playerId, 0);
    }

    @Override
    public void reset() {
        amountOfDamageReceivedThisTurn.clear();
    }

    @Override
    public AmountOfDamageAPlayerReceivedThisTurnWatcher copy() {
        return new AmountOfDamageAPlayerReceivedThisTurnWatcher(this);
    }
}
