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
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author North
 */
public final class TellingTime extends CardImpl {

    public TellingTime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Look at the top three cards of your library.
        // Put one of those cards into your hand, one on top of your library, and one on the bottom of your library.
        this.getSpellAbility().addEffect(new TellingTimeEffect());
    }

    public TellingTime(final TellingTime card) {
        super(card);
    }

    @Override
    public TellingTime copy() {
        return new TellingTime(this);
    }
}

class TellingTimeEffect extends OneShotEffect {

    public TellingTimeEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Look at the top three cards of your library. Put one of those cards into your hand, one on top of your library, and one on the bottom of your library.";
    }

    public TellingTimeEffect(final TellingTimeEffect effect) {
        super(effect);
    }

    @Override
    public TellingTimeEffect copy() {
        return new TellingTimeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }

        Cards cards = new CardsImpl();
        cards.addAll(controller.getLibrary().getTopCards(game, 3));
        controller.lookAtCards(sourceObject.getIdName(), cards, game);
        if (cards.isEmpty()) {
            return true;
        }
        Card card = pickCard(game, controller, cards, "card to put in your hand");
        if (card != null) {
            controller.moveCards(card, Zone.HAND, source, game);
            cards.remove(card);
        }
        if (cards.isEmpty()) {
            return true;
        }

        card = pickCard(game, controller, cards, "card to put on top of your library");
        if (card != null) {
            controller.moveCards(card, Zone.LIBRARY, source, game);
            cards.remove(card);
        }
        if (!cards.isEmpty()) {
            controller.putCardsOnBottomOfLibrary(cards, game, source, false);
        }
        return true;
    }

    private Card pickCard(Game game, Player player, Cards cards, String message) {
        if (cards.isEmpty()) {
            return null;
        }
        if (cards.size() == 1) {
            Card card = cards.getRandom(game);
            cards.remove(card);
            return card;
        }

        TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard(message));
        if (player.choose(Outcome.Benefit, cards, target, game)) {
            Card card = cards.get(target.getFirstTarget(), game);
            if (card != null) {
                cards.remove(card);
                return card;
            }
        }

        return null;
    }
}
