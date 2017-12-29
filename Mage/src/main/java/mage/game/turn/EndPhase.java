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

package mage.game.turn;

import mage.constants.PhaseStep;
import mage.constants.TurnPhase;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class EndPhase extends Phase {

    public EndPhase() {
        this.type = TurnPhase.END;
        this.event = EventType.END_PHASE;
        this.preEvent = EventType.END_PHASE_PRE;
        this.postEvent = EventType.END_PHASE_POST;
        this.steps.add(new EndStep());
        this.steps.add(new CleanupStep());
    }

    public EndPhase(final EndPhase phase) {
        super(phase);
    }

    @Override
    protected void playStep(Game game) {
        if (currentStep.getType() == PhaseStep.CLEANUP) {
            game.getTurn().setEndTurnRequested(false); // so triggers trigger again
            currentStep.beginStep(game, activePlayerId);
            // 514.3a At this point, the game checks to see if any state-based actions would be performed 
            // and/or any triggered abilities are waiting to be put onto the stack (including those that 
            // trigger "at the beginning of the next cleanup step"). If so, those state-based actions are 
            // performed, then those triggered abilities are put on the stack, then the active player gets
            // priority. Players may cast spells and activate abilities. Once the stack is empty and all players
            // pass in succession, another cleanup step begins
            if (game.checkStateAndTriggered()) {
                game.playPriority(activePlayerId, true);
                playStep(game);
            }
            currentStep.endStep(game, activePlayerId);
        }
        else {
            super.playStep(game);
        }
    }

    @Override
    public EndPhase copy() {
        return new EndPhase(this);
    }

}
