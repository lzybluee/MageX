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

package mage.game.draft;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.MageItem;
import mage.cards.ExpansionSet;
import mage.game.events.Listener;
import mage.game.events.PlayerQueryEvent;
import mage.game.events.TableEvent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public interface Draft extends MageItem, Serializable {

    void addPlayer(Player player);
    Collection<DraftPlayer> getPlayers();
    boolean replacePlayer(Player oldPlayer, Player newPlayer);
    DraftPlayer getPlayer(UUID playerId);
    int getNumberBoosters();
    DraftCube getDraftCube();
    List<ExpansionSet> getSets();
    int getBoosterNum();
    int getCardNum();
    boolean addPick(UUID playerId, UUID cardId, Set<UUID> hiddenCards);
    void start();
    boolean isStarted();
    void setStarted();

    boolean allJoined();
    void leave(UUID playerId);
    void autoPick(UUID playerId);

    void addTableEventListener(Listener<TableEvent> listener);
    void fireUpdatePlayersEvent();
    void fireEndDraftEvent();
    void addPlayerQueryEventListener(Listener<PlayerQueryEvent> listener);
    void firePickCardEvent(UUID playerId);

    void resetBufferedCards();

    boolean isAbort();
    void setAbort(boolean abort);

}
