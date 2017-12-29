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
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.SplitSecondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.target.common.TargetActivatedOrTriggeredAbility;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class Trickbind extends CardImpl {

    public Trickbind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");


        // Split second
        this.addAbility(new SplitSecondAbility());

        // Counter target activated or triggered ability. If a permanent's ability is countered this way, activated abilities of that permanent can't be activated this turn.
        this.getSpellAbility().addEffect(new TrickbindCounterEffect());
        this.getSpellAbility().addTarget(new TargetActivatedOrTriggeredAbility());
    }

    public Trickbind(final Trickbind card) {
        super(card);
    }

    @Override
    public Trickbind copy() {
        return new Trickbind(this);
    }
}

class TrickbindCounterEffect extends OneShotEffect {

    public TrickbindCounterEffect() {
        super(Outcome.Detriment);
        staticText = "Counter target activated or triggered ability. If a permanent's ability is countered this way, activated abilities of that permanent can't be activated this turn";
    }

    public TrickbindCounterEffect(final TrickbindCounterEffect effect) {
        super(effect);
    }

    @Override
    public TrickbindCounterEffect copy() {
        return new TrickbindCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject stackObject = game.getStack().getStackObject(source.getFirstTarget());
        if(stackObject != null && game.getStack().counter(source.getFirstTarget(), source.getSourceId(), game)) {
            TrickbindCantActivateEffect effect = new TrickbindCantActivateEffect();
            effect.setTargetPointer(new FixedTarget(stackObject.getSourceId()));
            game.getContinuousEffects().addEffect(effect, source);
            return true;
        }
        return false;
    }

}

class TrickbindCantActivateEffect extends RestrictionEffect {

    public TrickbindCantActivateEffect() {
        super(Duration.EndOfTurn);
        staticText = "Activated abilities of that permanent can't be activated this turn";
    }

    public TrickbindCantActivateEffect(final TrickbindCantActivateEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return getTargetPointer().getFirst(game, source).equals(permanent.getId());
    }

    @Override
    public boolean canUseActivatedAbilities(Permanent permanent, Ability source, Game game) {
        return false;
    }

    @Override
    public TrickbindCantActivateEffect copy() {
        return new TrickbindCantActivateEffect(this);
    }

}