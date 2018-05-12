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
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public class GoblinMachinist extends CardImpl {

    public GoblinMachinist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // {2}{R}: Reveal cards from the top of your library until you reveal a nonland card. Goblin Machinist gets +X/+0 until end of turn, where X is that card's converted mana cost. Put the revealed cards on the bottom of your library in any order.
        this.addAbility(new SimpleActivatedAbility(new GoblinMachinistEffect(), new ManaCostsImpl("{2}{R}")));
    }

    public GoblinMachinist(final GoblinMachinist card) {
        super(card);
    }

    @Override
    public GoblinMachinist copy() {
        return new GoblinMachinist(this);
    }
}

class GoblinMachinistEffect extends OneShotEffect {

    public GoblinMachinistEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal cards from the top of your library until you reveal a nonland card. {this} gets +X/+0 until end of turn, where X is that card's converted mana cost. Put the revealed cards on the bottom of your library in any order";
    }

    public GoblinMachinistEffect(final GoblinMachinistEffect effect) {
        super(effect);
    }

    @Override
    public GoblinMachinistEffect copy() {
        return new GoblinMachinistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            CardsImpl cards = new CardsImpl();
            for (Card card : controller.getLibrary().getCards(game)) {
                if (card != null) {
                    cards.add(card);
                    if (!card.isLand()) {
                        if (card.getConvertedManaCost() > 0) {
                            game.addEffect(new BoostSourceEffect(card.getConvertedManaCost(), 0, Duration.EndOfTurn), source);
                        }
                        break;
                    }
                }
                controller.revealCards(source, cards, game);
                controller.putCardsOnBottomOfLibrary(cards, game, source, true);
            }
            return true;
        }
        return false;
    }
}
