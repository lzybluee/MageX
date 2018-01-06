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
package mage.abilities.effects.keyword;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public class ExploreSourceEffect extends OneShotEffect {

    public static final String RULE_TEXT = "it explores. <i>(Reveal the top card of your library. Put that card into your hand if it's a land. Otherwise, put a +1/+1 counter on this creature, then put the card back or put it into your graveyard.)</i>";

    public ExploreSourceEffect() {
        super(Outcome.Benefit);
        this.staticText = RULE_TEXT;
    }

    public ExploreSourceEffect(final ExploreSourceEffect effect) {
        super(effect);
    }

    @Override
    public ExploreSourceEffect copy() {
        return new ExploreSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return explorePermanent(game, source.getSourceId(), source);
    }

    public static boolean explorePermanent(Game game, UUID permanentId, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(permanentId);
        if (permanent == null) {
            return false;
        }
        Player permanentController = game.getPlayer(source.getControllerId());
        if (permanentController == null) {
            return false;
        }
        game.fireEvent(GameEvent.getEvent(GameEvent.EventType.EXPLORED, permanentId, source.getSourceId(), permanent.getControllerId()));
        if (permanentController.getLibrary().hasCards()) {
            Card card = permanentController.getLibrary().getFromTop(game);
            Cards cards = new CardsImpl();
            cards.add(card);
            permanentController.revealCards("Explored card", cards, game);

            if (card != null) {
                if (card.isLand()) {
                    card.moveToZone(Zone.HAND, source.getSourceId(), game, true);
                } else {
                    permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
                    if (permanentController.chooseUse(Outcome.Neutral, "Put " + card.getLogName() + " in your graveyard?", source, game)) {
                        card.moveToZone(Zone.GRAVEYARD, source.getSourceId(), game, true);
                        game.informPlayers(permanentController.getLogName() + " puts " + card.getLogName() + " into their graveyard.");
                    } else {
                        game.informPlayers(permanentController.getLogName() + " leaves " + card.getLogName() + " on top of their library.");
                    }
                }
            }
        }
        return true;
    }
}
