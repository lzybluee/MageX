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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author BetaSteward
 */
public final class Counterlash extends CardImpl {

    public Counterlash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}{U}");

        // Counter target spell. You may cast a nonland card in your hand that shares a card type with that spell without paying its mana cost.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new CounterlashEffect());
    }

    public Counterlash(final Counterlash card) {
        super(card);
    }

    @Override
    public Counterlash copy() {
        return new Counterlash(this);
    }
}

class CounterlashEffect extends OneShotEffect {

    public CounterlashEffect() {
        super(Outcome.Detriment);
        this.staticText = "Counter target spell. You may cast a nonland card in your hand that shares a card type with that spell without paying its mana cost";
    }

    public CounterlashEffect(final CounterlashEffect effect) {
        super(effect);
    }

    @Override
    public CounterlashEffect copy() {
        return new CounterlashEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject stackObject = game.getStack().getStackObject(source.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        if (stackObject != null && player != null) {
            game.getStack().counter(source.getFirstTarget(), source.getSourceId(), game);
            if (player.chooseUse(Outcome.PutCardInPlay, "Cast a nonland card in your hand that shares a card type with that spell without paying its mana cost?", source, game)) {
                FilterCard filter = new FilterCard();
                List<Predicate<MageObject>> types = new ArrayList<>();
                for (CardType type : stackObject.getCardType()) {
                    if (type != CardType.LAND) {
                        types.add(new CardTypePredicate(type));
                    }
                }
                filter.add(Predicates.or(types));
                TargetCardInHand target = new TargetCardInHand(filter);
                if (player.choose(Outcome.PutCardInPlay, target, source.getSourceId(), game)) {
                    Card card = player.getHand().get(target.getFirstTarget(), game);
                    if (card != null) {
                        player.cast(card.getSpellAbility(), game, true, new MageObjectReference(source.getSourceObject(game), game));
                    }
                }
            }
            return true;
        }
        return false;
    }
}
