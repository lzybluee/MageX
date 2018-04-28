/*
* Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
 */
package mage.client.util;

import java.util.List;
import java.util.Map;
import mage.cards.Card;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.view.*;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class CardsViewUtil {

    public static CardsView convertSimple(SimpleCardsView view) {
        CardsView cards = new CardsView();

        for (SimpleCardView simple : view.values()) {
            CardInfo cardInfo = CardRepository.instance.findCard(simple.getExpansionSetCode(), simple.getCardNumber());
            Card card = cardInfo != null ? cardInfo.getMockCard() : null;
            if (card != null) {
                cards.put(simple.getId(), new CardView(card, simple.getId()));
            }
        }

        return cards;
    }

    public static CardsView convertSimple(SimpleCardsView view, Map<String, Card> loadedCards) {
        CardsView cards = new CardsView();

        for (SimpleCardView simple : view.values()) {
            String key = simple.getExpansionSetCode() + '_' + simple.getCardNumber();
            Card card = loadedCards.get(key);
            if (card == null) {
                CardInfo cardInfo = CardRepository.instance.findCard(simple.getExpansionSetCode(), simple.getCardNumber());
                card = cardInfo != null ? cardInfo.getMockCard() : null;
                loadedCards.put(key, card);
            }
            if (card != null) {
                cards.put(simple.getId(), new CardView(card, simple.getId()));
            }
        }

        return cards;
    }

    public static CardsView convertCommandObject(List<CommandObjectView> view) {
        CardsView cards = new CardsView();

        for (CommandObjectView commandObject : view) {
            if (commandObject instanceof EmblemView) {
                CardView cardView = new CardView((EmblemView) commandObject);
                cards.put(commandObject.getId(), cardView);
            } else if (commandObject instanceof PlaneView) {
                CardView cardView = null;
                cardView = new CardView((PlaneView) commandObject);
                cards.put(commandObject.getId(), cardView);                
            } else if (commandObject instanceof CommanderView) {
                cards.put(commandObject.getId(), (CommanderView) commandObject);
            }
        }

        return cards;
    }
}
