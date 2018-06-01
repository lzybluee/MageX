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
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author jeffwadsworth
 */
public final class PerishTheThought extends CardImpl {

    public PerishTheThought(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target opponent reveals their hand. You choose a card from it. That player shuffles that card into their library.
        this.getSpellAbility().addEffect(new PerishTheThoughtEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public PerishTheThought(final PerishTheThought card) {
        super(card);
    }

    @Override
    public PerishTheThought copy() {
        return new PerishTheThought(this);
    }
}

class PerishTheThoughtEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("card in target opponent's hand");

    public PerishTheThoughtEffect() {
        super(Outcome.Neutral);
        this.staticText = "Target opponent reveals their hand. You choose a card from it. That player shuffles that card into their library";
    }

    public PerishTheThoughtEffect(final PerishTheThoughtEffect effect) {
        super(effect);
    }

    @Override
    public PerishTheThoughtEffect copy() {
        return new PerishTheThoughtEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetOpponent = game.getPlayer(source.getFirstTarget());
        if (targetOpponent != null) {
            if (!targetOpponent.getHand().isEmpty()) {
                targetOpponent.revealCards("Perish the Thought", targetOpponent.getHand(), game);
                Player you = game.getPlayer(source.getControllerId());
                if (you != null) {
                    TargetCard target = new TargetCard(Zone.HAND, filter);
                    target.setNotTarget(true);
                    if (you.choose(Outcome.Neutral, targetOpponent.getHand(), target, game)) {
                        Card chosenCard = targetOpponent.getHand().get(target.getFirstTarget(), game);
                        if (chosenCard != null) {
                            chosenCard.moveToZone(Zone.LIBRARY, source.getSourceId(), game, false);
                            targetOpponent.shuffleLibrary(source, game);
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
