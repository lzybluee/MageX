/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.abilities.effects.common;

import mage.constants.Outcome;
import mage.constants.Zone;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PlayTargetWithoutPayingManaEffect extends OneShotEffect {

    public PlayTargetWithoutPayingManaEffect() {
        super(Outcome.GainControl);
    }

    public PlayTargetWithoutPayingManaEffect(final PlayTargetWithoutPayingManaEffect effect) {
        super(effect);
    }

    @Override
    public PlayTargetWithoutPayingManaEffect copy() {
        return new PlayTargetWithoutPayingManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card target = (Card) game.getObject(source.getFirstTarget());
        if (controller != null && target != null) {
            return controller.cast(target.getSpellAbility(), game, true);
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        if (!mode.getTargets().isEmpty()) {
            Target target = mode.getTargets().get(0);
            if (mode.getTargets().get(0).getZone() == Zone.HAND) {
                sb.append("you may put ").append(target.getTargetName()).append(" from your hand onto the battlefield");
            }
            else {
                sb.append("you may cast target ").append(target.getTargetName()).append(" without paying its mana cost");
            }
        }
        return sb.toString();
    }
}
