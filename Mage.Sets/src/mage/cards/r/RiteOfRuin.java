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
package mage.cards.r;

import java.util.*;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public class RiteOfRuin extends CardImpl {

    public RiteOfRuin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{R}{R}");

        // Choose an order for artifacts, creatures, and lands. Each player sacrifices one permanent of the first type, sacrifices two of the second type, then sacrifices three of the third type.
        this.getSpellAbility().addEffect(new RiteOfRuinEffect());
    }

    public RiteOfRuin(final RiteOfRuin card) {
        super(card);
    }

    @Override
    public RiteOfRuin copy() {
        return new RiteOfRuin(this);
    }
}

class RiteOfRuinEffect extends OneShotEffect {

    public RiteOfRuinEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Choose an order for artifacts, creatures, and lands. Each player sacrifices one permanent of the first type, sacrifices two of the second type, then sacrifices three of the third type";
    }

    public RiteOfRuinEffect(final RiteOfRuinEffect effect) {
        super(effect);
    }

    @Override
    public RiteOfRuinEffect copy() {
        return new RiteOfRuinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Set<String> choices = new HashSet<>();
        choices.add("Artifacts");
        choices.add("Creatures");
        choices.add("Lands");

        LinkedList<CardType> order = new LinkedList<>();
        ChoiceImpl choice = new ChoiceImpl(true);
        choice.setMessage("Choose a card type");
        choice.setChoices(choices);

        while (choices.size() > 1) {
            if (!controller.choose(Outcome.Sacrifice, choice, game)) {
                return false;
            }
            order.add(getCardType(choice.getChoice()));
            choices.remove(choice.getChoice());
            choice.clearChoice();
        }
        order.add(getCardType(choices.iterator().next()));

        int count = 1;
        for (CardType cardType : order) {
            FilterControlledPermanent filter = new FilterControlledPermanent(cardType + " you control");
            filter.add(new CardTypePredicate(cardType));
            new SacrificeAllEffect(count, filter).apply(game, source);
            count++;
        }

        return true;
    }

    private CardType getCardType(String type) {
        if ("Artifacts".equals(type)) {
            return CardType.ARTIFACT;
        }
        if ("Creatures".equals(type)) {
            return CardType.CREATURE;
        }
        if ("Lands".equals(type)) {
            return CardType.LAND;
        }
        return null;
    }
}
