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

package mage.view;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ChatMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private Date time;
    private String message;
    private MessageColor color;
    private SoundToPlay soundToPlay;
    private MessageType messageType;

    public enum MessageColor {
        BLACK, RED, GREEN, BLUE, ORANGE, YELLOW
    }

    public enum MessageType {
        USER_INFO, STATUS, GAME, TALK, WHISPER_FROM, WHISPER_TO
    }

    public enum SoundToPlay {
        PlayerLeft, PlayerQuitTournament, PlayerSubmittedDeck, PlayerWhispered
    }

    public ChatMessage(String username, String message, Date time, MessageColor color) {
        this(username, message, time, color, null);
    }

    public ChatMessage(String username, String message, Date time, MessageColor color, SoundToPlay soundToPlay) {
        this(username, message, time, color, MessageType.TALK, soundToPlay);
    }
    
    public ChatMessage(String username, String message, Date time, MessageColor color, MessageType messageType, SoundToPlay soundToPlay) {
        this.username = username;
        this.message = message;
        this.time = time;
        this.color = color;
        this.messageType = messageType;
        this.soundToPlay = soundToPlay;
    }

    public String getMessage() {
        return message;
    }

    public MessageColor getColor() {
        return color;
    }

    public boolean isUserMessage() {
        return color != null && (color==MessageColor.BLUE || color==MessageColor.YELLOW);
    }

    public boolean isStatusMessage() {
        return color != null && color== MessageColor.ORANGE;
    }

    public String getUsername() {
        return username;
    }

    public Date getTime() {
        return time;
    }

    public SoundToPlay getSoundToPlay() {
        return soundToPlay;
    }

    public MessageType getMessageType() {
        return messageType;
    }

}
