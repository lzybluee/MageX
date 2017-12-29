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
package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterObject;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;

/**
 *
 * @author LevelX2
 */
public class CantBeTargetedTargetEffect extends ContinuousRuleModifyingEffectImpl {

    private final FilterObject filterSource;
    private final TargetController targetController;

    public CantBeTargetedTargetEffect(FilterObject filterSource, Duration duration) {
        this(filterSource, duration, TargetController.ANY);
    }

    public CantBeTargetedTargetEffect(FilterObject filterSource, Duration duration, TargetController targetController) {
        super(duration, Outcome.Benefit, false, false);
        this.targetController = targetController;
        this.filterSource = filterSource;
    }

    public CantBeTargetedTargetEffect(final CantBeTargetedTargetEffect effect) {
        super(effect);
        this.filterSource = effect.filterSource.copy();
        this.targetController = effect.targetController;
    }

    @Override
    public CantBeTargetedTargetEffect copy() {
        return new CantBeTargetedTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TARGET;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (getTargetPointer().getTargets(game, source).contains(event.getTargetId())) {
            if (targetController == TargetController.OPPONENT
                    && !game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
                return false;
            }
            StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
            MageObject sourceObject;
            if (stackObject instanceof StackAbility) {
                sourceObject = ((StackAbility) stackObject).getSourceObject(game);
            } else {
                sourceObject = stackObject;
            }
            if (sourceObject != null && filterSource.match(sourceObject, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        if (!mode.getTargets().isEmpty()) {
            sb.append("target ").append(mode.getTargets().get(0).getTargetName());
        }
        sb.append(" can't be the target of ");
        sb.append(filterSource.getMessage());
        if (!duration.toString().isEmpty()) {
            sb.append(' ');
            if (duration == Duration.EndOfTurn) {
                sb.append("this turn");
            } else {
                sb.append(duration.toString());
            }
        }
        return sb.toString();
    }

}
