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
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class Counterbalance extends CardImpl {

    public Counterbalance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{U}{U}");


        // Whenever an opponent casts a spell, you may reveal the top card of your library. If you do, counter that spell if it has the same converted mana cost as the revealed card.
        this.addAbility(new SpellCastOpponentTriggeredAbility(Zone.BATTLEFIELD, new CounterbalanceEffect(), StaticFilters.FILTER_SPELL, true, SetTargetPointer.SPELL));
    }

    public Counterbalance(final Counterbalance card) {
        super(card);
    }

    @Override
    public Counterbalance copy() {
        return new Counterbalance(this);
    }
}

class CounterbalanceEffect extends OneShotEffect {

    public CounterbalanceEffect() {
        super(Outcome.Neutral);
        this.staticText = "you may reveal the top card of your library. If you do, counter that spell if it has the same converted mana cost as the revealed card";
    }

    public CounterbalanceEffect(final CounterbalanceEffect effect) {
        super(effect);
    }

    @Override
    public CounterbalanceEffect copy() {
        return new CounterbalanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            Spell spell = (Spell) game.getStack().getStackObject(targetPointer.getFirst(game, source));
            if (spell != null) {
                Card topcard = controller.getLibrary().getFromTop(game);
                if (topcard != null) {
                    CardsImpl cards = new CardsImpl();
                    cards.add(topcard);
                    controller.revealCards(sourcePermanent.getName(), cards, game);
                    if (topcard.getConvertedManaCost() == spell.getConvertedManaCost()) {
                        return game.getStack().counter(spell.getId(), source.getSourceId(), game);
                    }
                }
                return true;
            }
        }
        return false;
    }


}
