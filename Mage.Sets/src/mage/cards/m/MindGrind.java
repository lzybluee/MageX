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
import mage.abilities.Ability;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.OneShotEffect;
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

/**
 *
 * @author LevelX2
 */
public final class MindGrind extends CardImpl {

    public MindGrind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{B}");

        // Each opponent reveals cards from the top of their library until he or she reveals X land cards, then puts all cards revealed this way into their graveyard. X can't be 0.
        this.getSpellAbility().addEffect(new MindGrindEffect());
        for (VariableCost cost : this.getSpellAbility().getManaCosts().getVariableCosts()) {
            if (cost instanceof VariableManaCost) {
                ((VariableManaCost) cost).setMinX(1);
                break;
            }
        }
    }

    public MindGrind(final MindGrind card) {
        super(card);
    }

    @Override
    public MindGrind copy() {
        return new MindGrind(this);
    }
}

class MindGrindEffect extends OneShotEffect {

    public MindGrindEffect() {
        super(Outcome.Discard);
        this.staticText = "Each opponent reveals cards from the top of their library until he or she reveals X land cards, then puts all cards revealed this way into their graveyard. X can't be 0";
    }

    public MindGrindEffect(final MindGrindEffect effect) {
        super(effect);
    }

    @Override
    public MindGrindEffect copy() {
        return new MindGrindEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = source.getManaCostsToPay().getX();
        if (xValue < 1) {
            return false;
        }
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(opponentId);
            if (player == null) {
                continue;
            }
            int landsToReveal = xValue;
            Cards cards = new CardsImpl();
            for (Card card : player.getLibrary().getCards(game)) {
                cards.add(card);
                if (card.isLand() && --landsToReveal == 0) {
                    break;
                }
            }
            player.revealCards(source, "from " + player.getName(), cards, game);
            player.moveCards(cards, Zone.GRAVEYARD, source, game);
        }
        return true;
    }
}
