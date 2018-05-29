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
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
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
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author North
 */
public final class DiabolicRevelation extends CardImpl {

    public DiabolicRevelation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{3}{B}{B}");


        // Search your library for up to X cards and put those cards into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new DiabolicRevelationEffect());
    }

    public DiabolicRevelation(final DiabolicRevelation card) {
        super(card);
    }

    @Override
    public DiabolicRevelation copy() {
        return new DiabolicRevelation(this);
    }
}

class DiabolicRevelationEffect extends OneShotEffect {

    public DiabolicRevelationEffect() {
        super(Outcome.Benefit);
        this.staticText = "Search your library for up to X cards and put those cards into your hand. Then shuffle your library";
    }

    public DiabolicRevelationEffect(final DiabolicRevelationEffect effect) {
        super(effect);
    }

    @Override
    public DiabolicRevelationEffect copy() {
        return new DiabolicRevelationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = new ManacostVariableValue().calculate(game, source, this);
        TargetCardInLibrary target = new TargetCardInLibrary(0, amount, new FilterCard());

        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        if (player.searchLibrary(target, game)) {
            for (UUID cardId : target.getTargets()) {
                Card card = player.getLibrary().remove(cardId, game);
                if (card != null) {
                    card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                }
            }
        }

        player.shuffleLibrary(source, game);
        return true;
    }
}
