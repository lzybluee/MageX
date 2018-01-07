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
import mage.util.CardUtil;

/**
 * @author Viserion
 */
public class PlayAdditionalLandsControllerEffect extends ContinuousEffectImpl {

    protected int additionalCards;

    public PlayAdditionalLandsControllerEffect(int additionalCards, Duration duration) {
        super(duration, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        this.additionalCards = additionalCards;
        setText();
    }

    public PlayAdditionalLandsControllerEffect(final PlayAdditionalLandsControllerEffect effect) {
        super(effect);
        this.additionalCards = effect.additionalCards;
    }

    @Override
    public PlayAdditionalLandsControllerEffect copy() {
        return new PlayAdditionalLandsControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (player.getLandsPerTurn() == Integer.MAX_VALUE || this.additionalCards == Integer.MAX_VALUE) {
                player.setLandsPerTurn(Integer.MAX_VALUE);
            } else {
                player.setLandsPerTurn(player.getLandsPerTurn() + this.additionalCards);
            }
            return true;
        }
        return true;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append("You may play ");
        if (additionalCards == Integer.MAX_VALUE) {
            sb.append("any number of");
        } else {
            sb.append(CardUtil.numberToText(additionalCards, "an"));
        }
        sb.append(" additional land").append((additionalCards == 1 ? "" : "s"))
                .append(duration == Duration.EndOfTurn ? " this turn" : " on each of your turns");
        staticText = sb.toString();
    }

}
