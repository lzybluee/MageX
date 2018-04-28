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
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public class ExileCardYouChooseTargetOpponentEffect extends OneShotEffect {

    private FilterCard filter;

    public ExileCardYouChooseTargetOpponentEffect() {
        this(new FilterCard("a card"));
    }

    public ExileCardYouChooseTargetOpponentEffect(FilterCard filter) {
        super(Outcome.Discard);
        staticText = new StringBuilder("Target opponent reveals their hand. You choose ")
                .append(filter.getMessage()).append(" from it and exile that card").toString();
        this.filter = filter;
    }

    public ExileCardYouChooseTargetOpponentEffect(final ExileCardYouChooseTargetOpponentEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        Card sourceCard = game.getCard(source.getSourceId());
        if (controller != null && opponent != null) {
            if (!opponent.getHand().isEmpty()) {
                opponent.revealCards(sourceCard != null ? sourceCard.getIdName() + " (" + sourceCard.getZoneChangeCounter(game) + ')' : "Exile", opponent.getHand(), game);
                TargetCard target = new TargetCard(Zone.HAND, filter);
                if (controller.choose(Outcome.Exile, opponent.getHand(), target, game)) {
                    Card card = opponent.getHand().get(target.getFirstTarget(), game);
                    if (card != null) {
                        controller.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.HAND, true);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public ExileCardYouChooseTargetOpponentEffect copy() {
        return new ExileCardYouChooseTargetOpponentEffect(this);
    }

}
