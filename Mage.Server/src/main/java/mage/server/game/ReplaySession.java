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

package mage.server.game;

import java.util.UUID;

import mage.game.Game;
import mage.game.GameState;
import mage.interfaces.callback.ClientCallback;
import mage.interfaces.callback.ClientCallbackMethod;
import mage.server.UserManager;
import mage.view.GameView;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ReplaySession implements GameCallback {

    private final GameReplay replay;
    protected final UUID userId;

    ReplaySession(UUID gameId, UUID userId) {
        this.replay = new GameReplay(gameId);
        this.userId = userId;
    }

    public void replay() {
        replay.start();
        UserManager.instance.getUser(userId).ifPresent(user ->
                user.fireCallback(new ClientCallback(ClientCallbackMethod.REPLAY_INIT, replay.getGame().getId(), new GameView(replay.next(), replay.getGame(), null, null))));

    }

    public void stop() {
        gameResult("stopped replay");
    }

    public synchronized void next() {
        updateGame(replay.next(), replay.getGame());
    }

    public synchronized void next(int moves) {
        for (int i = 0; i < moves; i++) {
            replay.next();
        }
        updateGame(replay.next(), replay.getGame());
    }

    public synchronized void previous() {
        updateGame(replay.previous(), replay.getGame());
    }

    @Override
    public void gameResult(final String result) {
        UserManager.instance.getUser(userId).ifPresent(user ->
                user.fireCallback(new ClientCallback(ClientCallbackMethod.REPLAY_DONE, replay.getGame().getId(), result)));

        ReplayManager.instance.endReplay(replay.getGame().getId(), userId);
    }

    private void updateGame(final GameState state, Game game) {
        if (state == null) {
            gameResult("game ended");
        } else {
            UserManager.instance.getUser(userId).ifPresent(user ->
                    user.fireCallback(new ClientCallback(ClientCallbackMethod.REPLAY_UPDATE, replay.getGame().getId(), new GameView(state, game, null, null))));

        }
    }

}
