/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
 */
package mage.view;

import java.io.Serializable;

/**
 *
 * @author LevelX2
 */
public class UsersView implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String flagName;
    private final String userName;
    private final String matchHistory;
    private final int matchQuitRatio;
    private final String tourneyHistory;
    private final int tourneyQuitRatio;
    private final String infoGames;
    private final String infoPing;
    private final int generalRating;
    private final int constructedRating;
    private final int limitedRating;

    public UsersView(String flagName, String userName, String matchHistory, int matchQuitRatio,
            String tourneyHistory, int tourneyQuitRatio, String infoGames, String infoPing,
            int generalRating, int constructedRating, int limitedRating) {
        this.flagName = flagName;
        this.matchHistory = matchHistory;
        this.matchQuitRatio = matchQuitRatio;
        this.tourneyHistory = tourneyHistory;
        this.tourneyQuitRatio = tourneyQuitRatio;
        this.userName = userName;
        this.infoGames = infoGames;
        this.infoPing = infoPing;
        this.generalRating = generalRating;
        this.constructedRating = constructedRating;
        this.limitedRating = limitedRating;
    }

    public String getFlagName() {
        return flagName;
    }

    public String getUserName() {
        return userName;
    }

    public String getMatchHistory() {
        return matchHistory;
    }

    public int getMatchQuitRatio() {
        return matchQuitRatio;
    }

    public String getTourneyHistory() {
        return tourneyHistory;
    }

    public int getTourneyQuitRatio() {
        return tourneyQuitRatio;
    }

    public String getInfoGames() {
        return infoGames;
    }

    public String getInfoPing() {
        return infoPing;
    }

    public int getGeneralRating() {
        return generalRating;
    }

    public int getConstructedRating() {
        return constructedRating;
    }

    public int getLimitedRating() {
        return limitedRating;
    }
}
