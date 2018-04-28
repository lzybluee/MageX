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
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class InduceParanoia extends CardImpl {

    public InduceParanoia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}{U}");
        
        // Counter target spell. If {B} was spent to cast Induce Paranoia, that spell's controller puts the top X cards of their library into their graveyard, where X is the spell's converted mana cost.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new InduceParanoiaEffect(), 
                new CounterTargetEffect(),
                new ManaWasSpentCondition(ColoredManaSymbol.B), "Counter target spell. If {B} was spent to cast {this}, that spell's controller puts the top X cards of their library into their graveyard, where X is the spell's converted mana cost."));
                
        // Counter target spell. 
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    public InduceParanoia(final InduceParanoia card) {
        super(card);
    }

    @Override
    public InduceParanoia copy() {
        return new InduceParanoia(this);
    }
}

class InduceParanoiaEffect extends OneShotEffect {

    InduceParanoiaEffect() {
        super(Outcome.Detriment);
        this.staticText = "Counter target spell. If {B} was spent to cast {this}, that spell's controller puts the top X cards of their library into their graveyard, where X is the spell's converted mana cost.";
    }

    InduceParanoiaEffect(final InduceParanoiaEffect effect) {
        super(effect);
    }

    @Override
    public InduceParanoiaEffect copy() {
        return new InduceParanoiaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject spell = game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (spell != null) { 
            game.getStack().counter(spell.getId(), source.getSourceId(), game);
            int spellCMC = spell.getConvertedManaCost();
            Player player = game.getPlayer(spell.getControllerId());
            if (player != null) {
                player.moveCards(player.getLibrary().getTopCards(game, spellCMC), Zone.GRAVEYARD, source, game);
                return true;
            }
        }
        return false;
    }
}