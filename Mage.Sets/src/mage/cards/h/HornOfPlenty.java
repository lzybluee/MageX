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
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class HornOfPlenty extends CardImpl {

    public HornOfPlenty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{6}");

        // Whenever a player casts a spell, he or she may pay {1}. If that player does, he or she draws a card at the beginning of the next end step.
        this.addAbility(new SpellCastAllTriggeredAbility(new HornOfPlentyEffect(), new FilterSpell("a spell"), false, SetTargetPointer.PLAYER));
    }

    public HornOfPlenty(final HornOfPlenty card) {
        super(card);
    }

    @Override
    public HornOfPlenty copy() {
        return new HornOfPlenty(this);
    }
}

class HornOfPlentyEffect extends OneShotEffect {

    public HornOfPlentyEffect() {
        super(Outcome.Detriment);
        this.staticText = "he or she may pay {1}. If that player does, he or she draws a card at the beginning of the next end step";
    }

    public HornOfPlentyEffect(final HornOfPlentyEffect effect) {
        super(effect);
    }

    @Override
    public HornOfPlentyEffect copy() {
        return new HornOfPlentyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player caster = game.getPlayer(targetPointer.getFirst(game, source));
        if (caster != null) {
            if (caster.chooseUse(Outcome.DrawCard, "Pay {1} to draw a card at the beginning of the next end step?", source, game)) {
                Cost cost = new ManaCostsImpl("{1}");
                if (cost.pay(source, game, source.getSourceId(), caster.getId(), false, null)) {
                    Effect effect = new DrawCardTargetEffect(1);
                    effect.setTargetPointer(new FixedTarget(caster.getId()));
                    return new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect, TargetController.ANY)).apply(game, source);
                }
            }
            return true;
        }
        return false;
    }
}
