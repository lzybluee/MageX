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
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.DjinnToken;
import mage.players.Player;

/**
 *
 * @author KholdFuzion
 */
public final class BottleOfSuleiman extends CardImpl {

    public BottleOfSuleiman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {1}, Sacrifice Bottle of Suleiman: Flip a coin. If you lose the flip, Bottle of Suleiman deals 5 damage to you. If you win the flip, create a 5/5 colorless Djinn artifact creature token with flying.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BottleOfSuleimanEffect(), new GenericManaCost(1));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public BottleOfSuleiman(final BottleOfSuleiman card) {
        super(card);
    }

    @Override
    public BottleOfSuleiman copy() {
        return new BottleOfSuleiman(this);
    }
}

class BottleOfSuleimanEffect extends OneShotEffect {

    public BottleOfSuleimanEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Flip a coin. If you lose the flip, {this} deals 5 damage to you. If you win the flip, create a 5/5 colorless Djinn artifact creature token with flying.";
    }

    public BottleOfSuleimanEffect(final BottleOfSuleimanEffect effect) {
        super(effect);
    }

    @Override
    public BottleOfSuleimanEffect copy() {
        return new BottleOfSuleimanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        if (you != null) {
            if (you.flipCoin(game)) {
                DjinnToken token = new DjinnToken();
                token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
                return true;
            } else {
                you.damage(5, source.getSourceId(), game, false, true);
                return true;
            }

        }
        return false;
    }
}
