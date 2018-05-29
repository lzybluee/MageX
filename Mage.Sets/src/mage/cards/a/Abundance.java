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
package mage.cards.a;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public final class Abundance extends CardImpl {

    public Abundance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");

        // If you would draw a card, you may instead choose land or nonland and reveal cards from the top of your library until you reveal a card of the chosen kind. Put that card into your hand and put all other cards revealed this way on the bottom of your library in any order.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AbundanceReplacementEffect()));
    }

    public Abundance(final Abundance card) {
        super(card);
    }

    @Override
    public Abundance copy() {
        return new Abundance(this);
    }
}

class AbundanceReplacementEffect extends ReplacementEffectImpl {

    AbundanceReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would draw a card, you may instead choose land or nonland and reveal cards from the top of your library until you reveal a card of the chosen kind. Put that card into your hand and put all other cards revealed this way on the bottom of your library in any order";
    }

    AbundanceReplacementEffect(final AbundanceReplacementEffect effect) {
        super(effect);
    }

    @Override
    public AbundanceReplacementEffect copy() {
        return new AbundanceReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(event.getPlayerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            FilterCard filter = new FilterCard();
            if (controller.chooseUse(Outcome.Detriment, "Choose card type:",
                    source.getSourceObject(game).getLogName(), "land", "nonland", source, game)) {
                game.informPlayers(controller.getLogName() + "chooses land.");
                filter.add(new CardTypePredicate(CardType.LAND));
            } else {
                game.informPlayers(controller.getLogName() + "chooses nonland.");
                filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
            }
            Cards toReveal = new CardsImpl();
            Card selectedCard = null;
            for (Card card : controller.getLibrary().getCards(game)) {
                toReveal.add(card);
                if (filter.match(card, source.getSourceId(), source.getControllerId(), game)) {
                    selectedCard = card;
                    break;
                }

            }
            controller.moveCards(selectedCard, Zone.HAND, source, game);
            controller.revealCards(sourceObject.getIdName(), toReveal, game);
            toReveal.remove(selectedCard);
            controller.putCardsOnBottomOfLibrary(toReveal, game, source, true);

        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getPlayerId().equals(source.getControllerId())) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                return player.chooseUse(Outcome.Detriment, "Choose:", source.getSourceObject(game).getLogName(),
                        "land or nonland and reveal cards from the top", "normal card draw", source, game);
            }
        }
        return false;
    }
}
