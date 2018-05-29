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
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class BiomanticMastery extends CardImpl {

    public BiomanticMastery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G/U}{G/U}{G/U}");

        // <i>({GU} can be paid with either {G} or {U}.)</i>
        // Draw a card for each creature target player controls, then draw a card for each creature another target player controls.
        this.getSpellAbility().addEffect(new BiomanticMasteryEffect());
        this.getSpellAbility().addTarget(new TargetPlayer(2));

    }

    public BiomanticMastery(final BiomanticMastery card) {
        super(card);
    }

    @Override
    public BiomanticMastery copy() {
        return new BiomanticMastery(this);
    }
}

class BiomanticMasteryEffect extends OneShotEffect {

    public BiomanticMasteryEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Draw a card for each creature target player controls, then draw a card for each creature another target player controls";
    }

    public BiomanticMasteryEffect(final BiomanticMasteryEffect effect) {
        super(effect);
    }

    @Override
    public BiomanticMasteryEffect copy() {
        return new BiomanticMasteryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : getTargetPointer().getTargets(game, source)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int creatures = game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_CREATURE, playerId, game);
                    controller.drawCards(creatures, game);
                }
            }
            return true;
        }
        return false;
    }
}
