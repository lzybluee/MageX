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
package mage.abilities;

import java.util.UUID;
import mage.constants.AbilityType;
import mage.constants.AsThoughEffectType;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PlayLandAbility extends ActivatedAbilityImpl {

    public PlayLandAbility(String cardName) {
        super(AbilityType.PLAY_LAND, Zone.HAND);
        this.usesStack = false;
        this.name = "Play " + cardName;
    }

    public PlayLandAbility(PlayLandAbility ability) {
        super(ability);
    }

    @Override
    public boolean canActivate(UUID playerId, Game game) {
        if (!controlsAbility(playerId, game)
                && null == game.getContinuousEffects().asThough(getSourceId(), AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, playerId, game)) {
            return false;
        }
        //20091005 - 114.2a
        return game.getActivePlayerId().equals(playerId) && game.getPlayer(playerId).canPlayLand() && game.canPlaySorcery(playerId);
    }

    @Override
    public String getGameLogMessage(Game game) {
        return " plays " + getMessageText(game);
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public String getRule() {
        return this.name;
    }

    @Override
    public PlayLandAbility copy() {
        return new PlayLandAbility(this);
    }

}
