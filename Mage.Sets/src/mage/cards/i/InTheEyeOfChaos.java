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
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public class InTheEyeOfChaos extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an instant spell");
    static {
            filter.add(new CardTypePredicate(CardType.INSTANT));
    }

    public InTheEyeOfChaos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");
        addSuperType(SuperType.WORLD);


        // Whenever a player casts an instant spell, counter it unless that player pays {X}, where X is its converted mana cost.
        this.addAbility(new SpellCastAllTriggeredAbility(Zone.BATTLEFIELD, new InTheEyeOfChaosEffect(), filter, false, SetTargetPointer.SPELL));
    }

    public InTheEyeOfChaos(final InTheEyeOfChaos card) {
        super(card);
    }

    @Override
    public InTheEyeOfChaos copy() {
        return new InTheEyeOfChaos(this);
    }
}

class InTheEyeOfChaosEffect extends OneShotEffect {

    InTheEyeOfChaosEffect() {
        super(Outcome.Detriment);
        this.staticText = "counter it unless that player pays {X}, where X is its converted mana cost";
    }

    InTheEyeOfChaosEffect(final InTheEyeOfChaosEffect effect) {
        super(effect);
    }

    @Override
    public InTheEyeOfChaosEffect copy() {
        return new InTheEyeOfChaosEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject spell = game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (spell != null) {
            Player player = game.getPlayer(spell.getControllerId());
            if (player != null) {
                GenericManaCost cost = new GenericManaCost(spell.getConvertedManaCost());
                if (!cost.pay(source, game, source.getSourceId(), player.getId(), false)) {
                    game.getStack().counter(spell.getId(), source.getSourceId(), game);
                }
                return true;
            }
        }
        return false;
    }
}
