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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public final class MoldgrafMonstrosity extends CardImpl {

    public MoldgrafMonstrosity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}{G}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        this.addAbility(TrampleAbility.getInstance());
        // When Moldgraf Monstrosity dies, exile it, then return two creature cards at random from your graveyard to the battlefield.
        Effect effect = new ExileSourceEffect();
        effect.setText("");
        DiesTriggeredAbility ability = new DiesTriggeredAbility(effect);
        ability.addEffect(new MoldgrafMonstrosityEffect());
        this.addAbility(ability);
    }

    public MoldgrafMonstrosity(final MoldgrafMonstrosity card) {
        super(card);
    }

    @Override
    public MoldgrafMonstrosity copy() {
        return new MoldgrafMonstrosity(this);
    }
}

class MoldgrafMonstrosityEffect extends OneShotEffect {

    public MoldgrafMonstrosityEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "exile it, then return two creature cards at random from your graveyard to the battlefield";
    }

    public MoldgrafMonstrosityEffect(final MoldgrafMonstrosityEffect effect) {
        super(effect);
    }

    @Override
    public MoldgrafMonstrosityEffect copy() {
        return new MoldgrafMonstrosityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards possibleCards = new CardsImpl(controller.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, game));
            // Set<Card> cards = controller.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, game);
            Cards toBattlefield = new CardsImpl();
            for (int i = 0; i < 2; i++) {
                Card card = possibleCards.getRandom(game);
                if (card != null) {
                    toBattlefield.add(card);
                    possibleCards.remove(card);
                }
            }
            controller.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game);
            return true;
        }
        return false;
    }
}
