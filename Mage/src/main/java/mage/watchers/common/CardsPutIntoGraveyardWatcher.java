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
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import mage.MageObjectReference;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.watchers.Watcher;

/**
 * Counts amount of cards put into graveyards of players during the current
 * turn. Also the UUIDs of cards that went to graveyard from Battlefield this
 * turn.
 *
 * @author LevelX2
 */
public class CardsPutIntoGraveyardWatcher extends Watcher {

    private final Map<UUID, Integer> amountOfCardsThisTurn = new HashMap<>();
    private final Set<MageObjectReference> cardsPutToGraveyardFromBattlefield = new HashSet<>();

    public CardsPutIntoGraveyardWatcher() {
        super(CardsPutIntoGraveyardWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public CardsPutIntoGraveyardWatcher(final CardsPutIntoGraveyardWatcher watcher) {
        super(watcher);
        for (Entry<UUID, Integer> entry : watcher.amountOfCardsThisTurn.entrySet()) {
            amountOfCardsThisTurn.put(entry.getKey(), entry.getValue());
        }
        this.cardsPutToGraveyardFromBattlefield.addAll(watcher.cardsPutToGraveyardFromBattlefield);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.UNTAP_STEP_PRE) {
            reset();
        }
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD) {
            UUID playerId = event.getPlayerId();
            if (playerId != null && game.getCard(event.getTargetId()) != null) {
                amountOfCardsThisTurn.putIfAbsent(playerId, 0);
                amountOfCardsThisTurn.compute(playerId, (k, amount) -> amount += 1);

                if (((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD) {
                    cardsPutToGraveyardFromBattlefield.add(new MageObjectReference(event.getTargetId(), game));
                }
            }
        }
    }

    public int getAmountCardsPutToGraveyard(UUID playerId) {
        return amountOfCardsThisTurn.getOrDefault(playerId, 0);
    }

    public Set<MageObjectReference> getCardsPutToGraveyardFromBattlefield() {
        return cardsPutToGraveyardFromBattlefield;
    }

    @Override
    public void reset() {
        super.reset();
        amountOfCardsThisTurn.clear();
        cardsPutToGraveyardFromBattlefield.clear();
    }

    @Override
    public CardsPutIntoGraveyardWatcher copy() {
        return new CardsPutIntoGraveyardWatcher(this);
    }
}
