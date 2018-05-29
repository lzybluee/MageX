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
package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class Fork extends CardImpl {


    public Fork(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}{R}");

        // Copy target instant or sorcery spell, except that the copy is red. You may choose new targets for the copy.
        this.getSpellAbility().addEffect(new ForkEffect());
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_INSTANT_OR_SORCERY_SPELL));

    }

    public Fork(final Fork card) {
        super(card);
    }

    @Override
    public Fork copy() {
        return new Fork(this);
    }
}

class ForkEffect extends OneShotEffect {

    public ForkEffect() {
        super(Outcome.Copy);
        staticText = "Copy target instant or sorcery spell, except that the copy is red. You may choose new targets for the copy";
    }

    public ForkEffect(final ForkEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        if (spell != null) {
            Spell copy = spell.copySpell(source.getControllerId());
            copy.getColor(game).setRed(true);
            game.getStack().push(copy);
            copy.chooseNewTargets(game, controller.getId());
            game.fireEvent(new GameEvent(GameEvent.EventType.COPIED_STACKOBJECT, copy.getId(), spell.getId(), source.getControllerId()));
            return true;
        }
        return false;
    }

    @Override
    public ForkEffect copy() {
        return new ForkEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        sb.append("Copy target ").append(mode.getTargets().get(0).getTargetName()).append(", except that the copy is red. You may choose new targets for the copy");
        return sb.toString();
    }
}
