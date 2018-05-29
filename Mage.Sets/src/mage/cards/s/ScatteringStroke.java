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
package mage.cards.s;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfMainPhaseDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.mana.AddManaToManaPoolSourceControllerEffect;
import mage.abilities.effects.common.ClashEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class ScatteringStroke extends CardImpl {

    public ScatteringStroke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}{U}");

        // Counter target spell. Clash with an opponent. If you win, at the beginning of your next main phase, you may add {X}, where X is that spell's converted mana cost.
        this.getSpellAbility().addEffect(new ScatteringStrokeEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    public ScatteringStroke(final ScatteringStroke card) {
        super(card);
    }

    @Override
    public ScatteringStroke copy() {
        return new ScatteringStroke(this);
    }
}

class ScatteringStrokeEffect extends OneShotEffect {

    public ScatteringStrokeEffect() {
        super(Outcome.Benefit);
        this.staticText = "Counter target spell. Clash with an opponent. If you win, at the beginning of your next main phase, you may add {X}, where X is that spell's converted mana cost";
    }

    public ScatteringStrokeEffect(final ScatteringStrokeEffect effect) {
        super(effect);
    }

    @Override
    public ScatteringStrokeEffect copy() {
        return new ScatteringStrokeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) game.getStack().getStackObject(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && spell != null) {
            game.getStack().counter(spell.getId(), source.getSourceId(), game);
            if (ClashEffect.getInstance().apply(game, source)) {
                Effect effect = new AddManaToManaPoolSourceControllerEffect(new Mana(0, 0, 0, 0, 0, 0, 0, spell.getConvertedManaCost()));
                AtTheBeginOfMainPhaseDelayedTriggeredAbility delayedAbility
                        = new AtTheBeginOfMainPhaseDelayedTriggeredAbility(effect, true, TargetController.YOU, AtTheBeginOfMainPhaseDelayedTriggeredAbility.PhaseSelection.NEXT_MAIN);
                game.addDelayedTriggeredAbility(delayedAbility, source);
            }
            return true;
        }
        return false;
    }
}
