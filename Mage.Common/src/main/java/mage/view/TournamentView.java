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

package mage.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import mage.game.tournament.Round;
import mage.game.tournament.Tournament;
import mage.game.tournament.TournamentPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TournamentView implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String tournamentName;
    private final String tournamentType;
    private final String tournamentState;
    
    private final Date startTime;
    private final Date endTime;
    private final Date stepStartTime;
    private final Date serverTime;
    private final int constructionTime;
    private final boolean watchingAllowed;

    private final List<RoundView> rounds = new ArrayList<>();
    private final List<TournamentPlayerView> players = new ArrayList<>();
    private final String runningInfo;
    
    public TournamentView(Tournament tournament) {

        tournamentName = tournament.getOptions().getName();
        StringBuilder typeText = new StringBuilder(tournament.getOptions().getTournamentType());
        if (!tournament.getTournamentType().isLimited()) {
            typeText.append(" / ").append(tournament.getOptions().getMatchOptions().getDeckType());
        }
        if (tournament.getNumberRounds() > 0) {
            typeText.append(' ').append(tournament.getNumberRounds()).append(" rounds");
        } 
        tournamentType = typeText.toString();
        startTime = tournament.getStartTime();
        endTime = tournament.getEndTime();
        stepStartTime = tournament.getStepStartTime();
        constructionTime = tournament.getOptions().getLimitedOptions().getConstructionTime();
        watchingAllowed = tournament.getOptions().isWatchingAllowed();
        serverTime = new Date();
        tournamentState = tournament.getTournamentState();

        if (tournament.getTournamentState().equals("Drafting") && tournament.getDraft() != null) {
            runningInfo = "booster/card: " + tournament.getDraft().getBoosterNum() + '/' + (tournament.getDraft().getCardNum() -1);
        } else {
            runningInfo = "";
        }
        for (TournamentPlayer player: tournament.getPlayers()) {
            players.add(new TournamentPlayerView(player));
        }
        Collections.sort(players);
        for (Round round: tournament.getRounds()) {
            rounds.add(new RoundView(round));
        }
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public String getTournamentType() {
        return tournamentType;
    }

    public Date getStartTime() {
        return new Date(startTime.getTime());
    }

    public Date getEndTime() {
        if (endTime == null) {
            return null;
        }
        return new Date(endTime.getTime());
    }

    public boolean isWatchingAllowed() {
        return watchingAllowed;
    }

    public List<TournamentPlayerView> getPlayers() {
        return players;
    }

    public List<RoundView> getRounds() {
        return rounds;
    }

    public String getTournamentState() {
        return tournamentState;
    }

    public Date getStepStartTime() {
        return stepStartTime;
    }

    public int getConstructionTime() {
        return constructionTime;
    }

    public Date getServerTime() {
        return serverTime;
    }

    public String getRunningInfo() {
        return runningInfo;
    }
    
}
