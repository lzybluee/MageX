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
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.delayed.PactDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetSource;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Plopman
 */
public class InterventionPact extends CardImpl {

    public InterventionPact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{0}");

        this.color.setWhite(true);
        
        // The next time a source of your choice would deal damage to you this turn, prevent that damage. You gain life equal to the damage prevented this way.
        this.getSpellAbility().addEffect(new InterventionPactEffect());
        // At the beginning of your next upkeep, pay {1}{W}{W}. If you don't, you lose the game.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new PactDelayedTriggeredAbility(new ManaCostsImpl("{1}{W}{W}")), false));
    }

    public InterventionPact(final InterventionPact card) {
        super(card);
    }

    @Override
    public InterventionPact copy() {
        return new InterventionPact(this);
    }
}

class InterventionPactEffect extends OneShotEffect {

    public InterventionPactEffect() {
        super(Outcome.PreventDamage);
        this.staticText = "The next time a source of your choice would deal damage to you this turn, prevent that damage. You gain life equal to the damage prevented this way";
    }

    public InterventionPactEffect(final InterventionPactEffect effect) {
        super(effect);
    }

    @Override
    public InterventionPactEffect copy() {
        return new InterventionPactEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetSource();
            target.setRequired(true);
            target.setNotTarget(true);
            if (controller.chooseTarget(outcome, target, source, game)) {
                ContinuousEffect continuousEffect = new InterventionPactPreventDamageEffect();
                continuousEffect.setTargetPointer(new FixedTarget(target.getFirstTarget()));
                game.addEffect(continuousEffect, source);
            }
            return true;
        }
        return false;
    }
}

class InterventionPactPreventDamageEffect extends PreventionEffectImpl {

    public InterventionPactPreventDamageEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false, false);
        staticText = "The next time a source of your choice would deal damage to you this turn, prevent that damage. You gain life equal to the damage prevented this way";
    }

    public InterventionPactPreventDamageEffect(final InterventionPactPreventDamageEffect effect) {
        super(effect);
    }

    @Override
    public InterventionPactPreventDamageEffect copy() {
        return new InterventionPactPreventDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventEffectData = preventDamageAction(event, source, game);
        if (preventEffectData.getPreventedDamage() > 0) {
            used = true;
            Player player = game .getPlayer(source.getControllerId());
            if(player != null){
                player.gainLife(preventEffectData.getPreventedDamage(), game, source);
            }
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used && super.applies(event, source, game) && event.getTargetId().equals(source.getControllerId())) {
            if (event.getSourceId().equals(getTargetPointer().getFirst(game, source))) {
                return true;
            }
        }
        return false;
    }
}
