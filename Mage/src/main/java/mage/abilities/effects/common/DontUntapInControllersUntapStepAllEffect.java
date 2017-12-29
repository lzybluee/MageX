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

package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * 
 * 
 * @author LevelX2
 */

public class DontUntapInControllersUntapStepAllEffect extends ContinuousRuleModifyingEffectImpl {

    TargetController targetController;
    FilterPermanent filter;
    
    public DontUntapInControllersUntapStepAllEffect(Duration duration, TargetController targetController, FilterPermanent filter) {
        super(duration, Outcome.Detriment, false, false);
        this.targetController = targetController;
        this.filter = filter;
    }

    public DontUntapInControllersUntapStepAllEffect(final DontUntapInControllersUntapStepAllEffect effect) {
        super(effect);
        this.targetController = effect.targetController;
        this.filter = effect.filter;
    }

    @Override
    public DontUntapInControllersUntapStepAllEffect copy() {
        return new DontUntapInControllersUntapStepAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.UNTAP;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getTurn().getStepType() == PhaseStep.UNTAP) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null) {
                switch(targetController) {
                    case YOU:
                        if (!permanent.getControllerId().equals(source.getControllerId())) {
                            return false;
                        }
                        break;
                    case OPPONENT:
                        Player controller = game.getPlayer(source.getControllerId());
                        if (controller != null && !game.isOpponent(controller, permanent.getControllerId())) {
                            return false;
                        }
                        break;
                    case ANY:
                        break;
                    default:
                        throw new RuntimeException("Type of TargetController not supported!");
                }
                if (game.getActivePlayerId().equals(permanent.getControllerId()) && // controller's untap step
                        filter.match(permanent, source.getSourceId(), source.getControllerId(), game)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (!staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb =  new StringBuilder(filter.getMessage()).append(" don't untap during ");
        switch(targetController) {
            case ANY:
                sb.append("their controller's ");
                break;
            default:
                throw new RuntimeException("Type of TargetController not supported yet!");
        }        
        sb.append("untap steps");
        return sb.toString();
    }

}
