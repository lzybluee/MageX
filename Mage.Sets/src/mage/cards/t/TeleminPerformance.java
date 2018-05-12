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
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author jeffwadsworth
 */
public class TeleminPerformance extends CardImpl {

    public TeleminPerformance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{U}");

        // Target opponent reveals cards from the top of their library until he or she reveals a creature card. That player puts all noncreature cards revealed this way into their graveyard, then you put the creature card onto the battlefield under your control.
        this.getSpellAbility().addEffect(new TeleminPerformanceEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

    }

    public TeleminPerformance(final TeleminPerformance card) {
        super(card);
    }

    @Override
    public TeleminPerformance copy() {
        return new TeleminPerformance(this);
    }
}

class TeleminPerformanceEffect extends OneShotEffect {

    public TeleminPerformanceEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Target opponent reveals cards from the top of their library until he or she reveals a creature card. That player puts all noncreature cards revealed this way into their graveyard, then you put the creature card onto the battlefield under your control";
    }

    public TeleminPerformanceEffect(final TeleminPerformanceEffect effect) {
        super(effect);
    }

    @Override
    public TeleminPerformanceEffect copy() {
        return new TeleminPerformanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
            if (opponent != null) {
                Card creature = null;
                CardsImpl nonCreatures = new CardsImpl();
                CardsImpl reveal = new CardsImpl();
                for (Card card : opponent.getLibrary().getCards(game)) {
                    reveal.add(card);
                    if (card.isCreature()) {
                        creature = card;
                        break;
                    } else {
                        nonCreatures.add(card);
                    }
                }
                opponent.revealCards(source, reveal, game);
                opponent.moveCards(nonCreatures, Zone.GRAVEYARD, source, game);
                if (creature != null) {
                    game.applyEffects();
                    controller.moveCards(creature, Zone.BATTLEFIELD, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
