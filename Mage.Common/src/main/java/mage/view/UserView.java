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
import java.util.Date;

/**
 * Admin Console View
 *
 * @author BetaSteward_at_googlemail.com
 */
public class UserView implements Serializable {

    private final String userName;
    private final String host;
    private final String sessionId;
    private final Date timeConnected;
    private final Date lastActivity;
    private final String gameInfo;
    private final String userState;
    private final Date muteChatUntil;
    private final String clientVersion;
    private final String email;
    private final String userIdStr;

    public UserView(String userName, String host, String sessionId, Date timeConnected, Date lastActivity, String gameInfo, String userState, Date muteChatUntil, String clientVersion, String email, String userIdStr) {
        this.userName = userName;
        this.host = host;
        this.sessionId = sessionId;
        this.timeConnected = timeConnected;
        this.lastActivity = lastActivity;
        this.gameInfo = gameInfo;
        this.userState = userState;
        this.muteChatUntil = muteChatUntil;
        this.clientVersion = clientVersion;
        this.email = email;
        this.userIdStr = userIdStr;
    }

    public String getUserName() {
        return userName;
    }

    public String getHost() {
        return host;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getGameInfo() {
        return gameInfo;
    }

    public String getUserState() {
        return userState;
    }

    public Date getMuteChatUntil() {
        return muteChatUntil;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public Date getTimeConnected() {
        return timeConnected;
    }

    public Date getLastActivity() {
        return lastActivity;
    }

    public String getEmail() {
        return email;
    }

    public String getUserIdStr() {
        return userIdStr;
    }
}
