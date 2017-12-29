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
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInOpponentsGraveyard;

/**
 *
 * @author LevelX2
 */
public class MemoryPlunder extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant or sorcery card from an opponent's graveyard");
    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    public MemoryPlunder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U/B}{U/B}{U/B}{U/B}");


        // You may cast target instant or sorcery card from an opponent's graveyard without paying its mana cost.
        this.getSpellAbility().addEffect(new MemoryPlunderEffect());
        this.getSpellAbility().addTarget(new TargetCardInOpponentsGraveyard(filter));

    }

    public MemoryPlunder(final MemoryPlunder card) {
        super(card);
    }

    @Override
    public MemoryPlunder copy() {
        return new MemoryPlunder(this);
    }
}

class MemoryPlunderEffect extends OneShotEffect {

    public MemoryPlunderEffect() {
        super(Outcome.Benefit);
        this.staticText = "You may cast target instant or sorcery card from an opponent's graveyard without paying its mana cost";
    }

    public MemoryPlunderEffect(final MemoryPlunderEffect effect) {
        super(effect);
    }

    @Override
    public MemoryPlunderEffect copy() {
        return new MemoryPlunderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card != null) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null && player.chooseUse(Outcome.Benefit, "Cast " + card.getName() +" without paying cost?", source, game)) {
                player.cast(card.getSpellAbility(), game, true);
            }
        }
        return false;
    }
}
