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

import mage.game.match.MatchOptions;
import mage.players.PlayerType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TournamentOptions implements Serializable {

    protected String name;
    protected String tournamentType;
    protected List<PlayerType> playerTypes = new ArrayList<>();
    protected MatchOptions matchOptions;
    protected LimitedOptions limitedOptions;
    protected boolean watchingAllowed = true;
    protected boolean planeChase = false;
    protected int numberRounds;
    protected String password;
    protected int quitRatio;

    public TournamentOptions(String name, String matchType, int numSeats) {
        this.name = name;
        this.matchOptions = new MatchOptions("", matchType, numSeats > 2, numSeats);
    }

    public String getName() {
        return name;
    }

    public String getTournamentType() {
        return tournamentType;
    }

    public void setTournamentType(String tournamentType) {
        this.tournamentType = tournamentType;
    }

    public List<PlayerType> getPlayerTypes() {
        return playerTypes;
    }

    public MatchOptions getMatchOptions() {
        return matchOptions;
    }

    public void setLimitedOptions(LimitedOptions limitedOptions) {
        this.limitedOptions = limitedOptions;
    }

    public LimitedOptions getLimitedOptions() {
        return limitedOptions;
    }

    public boolean isWatchingAllowed() {
        return watchingAllowed;
    }

    public void setWatchingAllowed(boolean watchingAllowed) {
        this.watchingAllowed = watchingAllowed;
    }

    public boolean isPlaneChase() {
        return planeChase;
    }

    public void setPlaneChase(boolean planeChase) {
        this.planeChase = planeChase;
        this.matchOptions.setPlaneChase(planeChase);
    }    

    public int getNumberRounds() {
        return numberRounds;
    }

    public void setNumberRounds(int numberRounds) {
        this.numberRounds = numberRounds;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getQuitRatio() {
        return quitRatio;
    }

    public void setQuitRatio(int quitRatio) {
        this.quitRatio = quitRatio;
    }
}
