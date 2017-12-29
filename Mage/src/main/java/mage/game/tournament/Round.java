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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Round {

    private final int roundNum;
    private final Tournament tournament;
    private final List<TournamentPairing> pairs = new ArrayList<>();
    private final List<TournamentPlayer> playerByes = new ArrayList<>();

    public Round(int roundNum, Tournament tournament) {
        this.roundNum = roundNum;
        this.tournament = tournament;
    }

    public void addPairing(TournamentPairing match) {
        this.pairs.add(match);
    }

    public TournamentPairing getPairing(UUID pairId) {
        for (TournamentPairing pair: pairs) {
            if (pair.getId().equals(pairId)) {
                return pair;
            }
        }
        return null;
    }

    public List<TournamentPairing> getPairs() {
        return pairs;
    }

    public int getRoundNumber() {
        return this.roundNum;
    }

    public boolean isRoundOver() {
        boolean roundIsOver = true;
        for (TournamentPairing pair: pairs) {
            if (pair.getMatch() != null) {
                if (!pair.getMatch().hasEnded()) {
                    roundIsOver = false;
                } else {
                    if (!pair.isAlreadyPublished()) {
                        tournament.updateResults();
                        pair.setAlreadyPublished(true);
                        if (tournament instanceof TournamentSingleElimination) {
                            pair.eliminatePlayers();
                        }
                        // if it's the last round, finish all players for the tournament if their match is finished
                        if (getRoundNumber() == tournament.getNumberRounds()) {
                            pair.finishPlayersThatPlayedLastRound();
                        }
                    }
                }
            }
        }
        return roundIsOver;
    }

    public List<TournamentPlayer> getPlayerByes() {
        return playerByes;
    }
    
}
