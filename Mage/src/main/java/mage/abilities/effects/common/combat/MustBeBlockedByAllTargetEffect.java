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

package mage.abilities.effects.common.combat;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.RequirementEffect;
import mage.constants.AbilityType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.BlockedAttackerWatcher;

/**
 *
 * @author LevelX2
 */
public class MustBeBlockedByAllTargetEffect extends RequirementEffect {

    public MustBeBlockedByAllTargetEffect(Duration duration) {
        super(duration);
        staticText =  new StringBuilder("All creatures able to block target creature ")
                .append(this.getDuration() == Duration.EndOfTurn ? "this turn ":"")
                .append("do so").toString();
    }

    public MustBeBlockedByAllTargetEffect(final MustBeBlockedByAllTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {        
        Permanent attackingCreature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (attackingCreature != null && attackingCreature.isAttacking()) {
            if (source.getAbilityType() != AbilityType.STATIC) {
                BlockedAttackerWatcher blockedAttackerWatcher = (BlockedAttackerWatcher) game.getState().getWatchers().get(BlockedAttackerWatcher.class.getSimpleName());
                if (blockedAttackerWatcher != null && blockedAttackerWatcher.creatureHasBlockedAttacker(attackingCreature, permanent, game)) {
                    // has already blocked this turn, so no need to do again
                    return false;
                }
            }
            return permanent.canBlock(this.getTargetPointer().getFirst(game, source), game);
        }
        return false;
    }

    @Override
    public boolean mustAttack(Game game) {
        return false;
    }

    @Override
    public boolean mustBlock(Game game) {
        return true;
    }

    @Override
    public UUID mustBlockAttacker(Ability source, Game game) {
        return this.getTargetPointer().getFirst(game, source);
    }

    @Override
    public MustBeBlockedByAllTargetEffect copy() {
        return new MustBeBlockedByAllTargetEffect(this);
    }

}
