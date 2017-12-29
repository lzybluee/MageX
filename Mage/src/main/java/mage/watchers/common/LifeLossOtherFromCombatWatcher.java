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

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.Watcher;

/*
 *
 * @author Styxo
 */
public class LifeLossOtherFromCombatWatcher extends Watcher {

    private final Set<UUID> players = new HashSet<>();

    public LifeLossOtherFromCombatWatcher() {
        super(LifeLossOtherFromCombatWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public LifeLossOtherFromCombatWatcher(final LifeLossOtherFromCombatWatcher watcher) {
        super(watcher);
        this.players.addAll(watcher.players);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.LOST_LIFE && !event.getFlag()) {
            UUID playerId = event.getPlayerId();
            if (playerId != null) {
                players.add(playerId);
            }
        }
    }

    public boolean opponentLostLifeOtherFromCombat(UUID playerId, Game game) {
        Player player = game.getPlayer(playerId);
        if (player != null) {
            if (players.stream().anyMatch(damagedPlayerId -> player.hasOpponent(damagedPlayerId, game))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void reset() {
        super.reset();
        players.clear();
    }

    @Override
    public LifeLossOtherFromCombatWatcher copy() {
        return new LifeLossOtherFromCombatWatcher(this);
    }
}
