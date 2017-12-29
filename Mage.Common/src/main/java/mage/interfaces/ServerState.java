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

package mage.interfaces;

import mage.players.PlayerType;
import mage.utils.MageVersion;
import mage.view.GameTypeView;
import mage.view.TournamentTypeView;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ServerState implements Serializable {

    private final List<GameTypeView> gameTypes;
    private final List<TournamentTypeView> tournamentTypes;
    private final PlayerType[] playerTypes;
    private final String[] deckTypes;
    private final String[] draftCubes;
    private final boolean testMode;
    private final MageVersion version;
    private final long cardsContentVersion;
    private final long expansionsContentVersion;

    public ServerState(List<GameTypeView> gameTypes, List<TournamentTypeView> tournamentTypes,
                       PlayerType[] playerTypes, String[] deckTypes, String[] draftCubes, boolean testMode,
                       MageVersion version, long cardsContentVersion, long expansionsContentVersion) {
        this.gameTypes = gameTypes;
        this.tournamentTypes = tournamentTypes;
        this.playerTypes = playerTypes;
        this.deckTypes = deckTypes;
        this.draftCubes = draftCubes;
        this.testMode = testMode;
        this.version = version;
        this.cardsContentVersion = cardsContentVersion;
        this.expansionsContentVersion = expansionsContentVersion;

    }

    public List<GameTypeView> getGameTypes() {
        return gameTypes;
    }

    public List<GameTypeView> getTournamentGameTypes() {
        return gameTypes.stream()
                .filter(gameTypeView -> gameTypeView.getMinPlayers() == 2 && gameTypeView.getMaxPlayers() == 2)
                .collect(Collectors.toList());
    }

    public List<TournamentTypeView> getTournamentTypes() {
        return tournamentTypes;
    }

    public PlayerType[] getPlayerTypes() {
        return playerTypes;
    }

    public String[] getDeckTypes() {
        return deckTypes;
    }

    public String[] getDraftCubes() {
        return draftCubes;
    }

    public boolean isTestMode() {
        return testMode;
    }

    public MageVersion getVersion() {
        return version;
    }

    public long getCardsContentVersion() {
        return cardsContentVersion;
    }

    public long getExpansionsContentVersion() {
        return expansionsContentVersion;
    }

}
