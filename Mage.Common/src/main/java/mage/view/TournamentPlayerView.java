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
import mage.game.tournament.TournamentPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TournamentPlayerView implements Serializable, Comparable {

    private static final long serialVersionUID = 1L;

    private final String flagName;
    private final String name;
    private final String state;
    private final String results;
    private final String history;
    private final int points;
    private final boolean quit;

    TournamentPlayerView(TournamentPlayer tournamentPlayer) {
        this.name = tournamentPlayer.getPlayer().getName();
        StringBuilder sb = new StringBuilder(tournamentPlayer.getState().toString());
        String stateInfo = tournamentPlayer.getStateInfo();
        if (!stateInfo.isEmpty()) {
            sb.append(" (").append(stateInfo).append(')');
        }
        sb.append(tournamentPlayer.getDisconnectInfo());
        this.state = sb.toString();
        this.points = tournamentPlayer.getPoints();
        this.results = tournamentPlayer.getResults();
        this.quit = !tournamentPlayer.isInTournament();
        this.history = tournamentPlayer.getPlayer().getUserData().getHistory();
        this.flagName = tournamentPlayer.getPlayer().getUserData().getFlagName();
    }

    public String getName() {
        return this.name;
    }

    public String getState() {
        return state;
    }

    public int getPoints() {
        return this.points;
    }

    public String getResults() {
        return results;
    }

    public boolean hasQuit() {
        return quit;
    }

    @Override
    public int compareTo(Object t) {
        return ((TournamentPlayerView) t).getPoints() - this.getPoints();
    }

    public String getFlagName() {
        return flagName;
    }

    public String getHistory() {
        return history;
    }

}
