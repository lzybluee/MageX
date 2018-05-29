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
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class SphinxsTutelage extends CardImpl {

    public SphinxsTutelage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // Whenever you draw a card, target opponent puts the top two cards of their library into their graveyard. If they're both nonland cards that share a color, repeat this process.
        Ability ability = new DrawCardControllerTriggeredAbility(new SphinxsTutelageEffect(), false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // {5}{U}: Draw a card, then discard a card.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawDiscardControllerEffect(1, 1), new ManaCostsImpl<>("{5}{U}")));
    }

    public SphinxsTutelage(final SphinxsTutelage card) {
        super(card);
    }

    @Override
    public SphinxsTutelage copy() {
        return new SphinxsTutelage(this);
    }
}

class SphinxsTutelageEffect extends OneShotEffect {

    public SphinxsTutelageEffect() {
        super(Outcome.Benefit);
        this.staticText = "target opponent puts the top two cards of their library into their graveyard. If they're both nonland cards that share a color, repeat this process";
    }

    public SphinxsTutelageEffect(final SphinxsTutelageEffect effect) {
        super(effect);
    }

    @Override
    public SphinxsTutelageEffect copy() {
        return new SphinxsTutelageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        boolean colorShared;
        if (targetPlayer != null) {
            int possibleIterations = targetPlayer.getLibrary().size() / 2;
            int iteration = 0;
            do {
                iteration++;
                if (iteration > possibleIterations + 20) {
                    // 801.16. If the game somehow enters a "loop" of mandatory actions, repeating a sequence of events
                    // with no way to stop, the game is a draw for each player who controls an object that's involved in
                    // that loop, as well as for each player within the range of influence of any of those players. They
                    // leave the game. All remaining players continue to play the game.
                    game.setDraw(source.getControllerId());
                    return true;
                }
                colorShared = false;
                Cards cards = new CardsImpl(targetPlayer.getLibrary().getTopCards(game, 2));
                Card card1 = null;
                for (Card card : cards.getCards(game)) {
                    if (card.isLand()) {
                        break;
                    }
                    if (card1 == null) {
                        card1 = card;
                    } else {
                        colorShared = card1.getColor(game).shares(card.getColor(game));
                    }
                }
                targetPlayer.moveCards(cards, Zone.GRAVEYARD, source, game);
            } while (colorShared && targetPlayer.isInGame());
            return true;
        }
        return false;
    }
}
