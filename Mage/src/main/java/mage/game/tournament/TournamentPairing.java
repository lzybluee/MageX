/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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

package mage.game.tournament;

import java.util.UUID;
import mage.constants.TournamentPlayerState;
import mage.game.match.Match;
import mage.game.match.MatchPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TournamentPairing {

    private final UUID id = UUID.randomUUID();
    private UUID tableId;
    private Match match;
    private final TournamentPlayer player1;
    private final TournamentPlayer player2;
    private boolean alreadyPublished;
    
    public TournamentPairing(TournamentPlayer player1, TournamentPlayer player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.alreadyPublished = false;
    }

    public UUID getId() {
        return id;
    }

    public TournamentPlayer getPlayer1() {
        return this.player1;
    }

    public TournamentPlayer getPlayer2() {
        return this.player2;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    /**
     * Called by eliminate tournaments after each match
     */
    public void eliminatePlayers() {
        if (match != null && match.hasEnded()) {
            MatchPlayer mPlayer1 = match.getPlayer(player1.getPlayer().getId());
            MatchPlayer mPlayer2 = match.getPlayer(player2.getPlayer().getId());
            if (mPlayer1.hasQuit() || !mPlayer1.isMatchWinner()) {
                player1.setEliminated();
            }
            if (mPlayer2.hasQuit() || !mPlayer2.isMatchWinner()) {
                player2.setEliminated();
            }
        }
    }
    public void finishPlayersThatPlayedLastRound() {
        if (match != null && match.hasEnded()) {
            if (!player1.isEliminated()) {
                player1.setEliminated();
                player1.setState(TournamentPlayerState.FINISHED);
            }
            if (!player2.isEliminated()) {
                player2.setEliminated();
                player2.setState(TournamentPlayerState.FINISHED);
            }            
        }        
    }

    public void eliminateComputer() {
        if (!player1.getPlayer().isHuman()) {
            player1.setEliminated();
            return;
        }
        if (!player2.getPlayer().isHuman()) {
            player2.setEliminated();
        }
    }

    public UUID getTableId() {
        return tableId;
    }

    public void setTableId(UUID tableId) {
        this.tableId = tableId;
    }

    public boolean isAlreadyPublished() {
        return alreadyPublished;
    }

    public void setAlreadyPublished(boolean alreadyPublished) {
        this.alreadyPublished = alreadyPublished;
    }

}
