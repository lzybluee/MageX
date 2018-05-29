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
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class DazzlingReflection extends CardImpl {

    public DazzlingReflection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // You gain life equal to target creature's power. The next time that creature would deal damage this turn, prevent that damage.
        getSpellAbility().addEffect(new DazzlingReflectionEffect());
        getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public DazzlingReflection(final DazzlingReflection card) {
        super(card);
    }

    @Override
    public DazzlingReflection copy() {
        return new DazzlingReflection(this);
    }
}

class DazzlingReflectionEffect extends OneShotEffect {

    public DazzlingReflectionEffect() {
        super(Outcome.Benefit);
        this.staticText = "You gain life equal to target creature's power. The next time that creature would deal damage this turn, prevent that damage";
    }

    public DazzlingReflectionEffect(final DazzlingReflectionEffect effect) {
        super(effect);
    }

    @Override
    public DazzlingReflectionEffect copy() {
        return new DazzlingReflectionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent targetCreature = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
            controller.gainLife(targetCreature.getPower().getValue(), game, source);
            ContinuousEffect effect = new DazzlingReflectionPreventEffect();
            effect.setTargetPointer(new FixedTarget(targetCreature, game));
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}

class DazzlingReflectionPreventEffect extends PreventionEffectImpl {

    public DazzlingReflectionPreventEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false, false);
        staticText = "The next time that creature would deal damage this turn, prevent that damage";
    }

    public DazzlingReflectionPreventEffect(final DazzlingReflectionPreventEffect effect) {
        super(effect);
    }

    @Override
    public DazzlingReflectionPreventEffect copy() {
        return new DazzlingReflectionPreventEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        super.replaceEvent(event, source, game);
        discard();
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (event.getSourceId().equals(getTargetPointer().getFirst(game, source))) {
                return true;
            }
        }
        return false;
    }

}
