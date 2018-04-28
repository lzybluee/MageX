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
package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.players.Player;

/**
 * Each player may play an additional land on each of their turns.
 *
 * @author nantuko
 */
public class PlayAdditionalLandsAllEffect extends ContinuousEffectImpl {

    private int numExtraLands = 1;

    public PlayAdditionalLandsAllEffect() {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "Each player may play an additional land on each of their turns";
        numExtraLands = 1;
    }

    public PlayAdditionalLandsAllEffect(int numExtraLands) {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        this.numExtraLands = numExtraLands;
        if (numExtraLands == Integer.MAX_VALUE) {
            staticText = "Each player may play any number of additional lands on each of their turns";
        } else {
            staticText = "Each player may play an additional " + numExtraLands + " lands on each of their turns";
        }
    }

    public PlayAdditionalLandsAllEffect(final PlayAdditionalLandsAllEffect effect) {
        super(effect);
        this.numExtraLands = effect.numExtraLands;
        this.staticText = effect.staticText;
    }

    @Override
    public PlayAdditionalLandsAllEffect copy() {
        return new PlayAdditionalLandsAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        if (player != null) {
            if (numExtraLands == Integer.MAX_VALUE) {
                player.setLandsPerTurn(Integer.MAX_VALUE);
            } else {
                player.setLandsPerTurn(player.getLandsPerTurn() + numExtraLands);
            }
            return true;
        }
        return true;
    }
}
