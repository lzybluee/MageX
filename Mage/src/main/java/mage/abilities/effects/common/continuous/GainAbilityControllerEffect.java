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

package mage.abilities.effects.common.continuous;

import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GainAbilityControllerEffect extends ContinuousEffectImpl {

    protected Ability ability;

    /**
     * Add ability with Duration.WhileOnBattlefield
     * @param ability
     */
    public GainAbilityControllerEffect(Ability ability) {
        this(ability, Duration.WhileOnBattlefield);
    }

    /**
     * 
     * @param ability
     * @param duration custom - effect will be discarded as soon there is no sourceId - permanent on the battlefield
     */
    public GainAbilityControllerEffect(Ability ability, Duration duration) {
        super(duration, Layer.PlayerEffects, SubLayer.NA, Outcome.AddAbility);
        this.ability = ability;
        staticText = "You have " + ability.getRule();
        if (!duration.toString().isEmpty()) {
            staticText += ' ' + duration.toString();
        }
    }

    public GainAbilityControllerEffect(final GainAbilityControllerEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
    }

    @Override
    public GainAbilityControllerEffect copy() {
        return new GainAbilityControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.addAbility(ability);
            if (duration == Duration.Custom) {
                if (game.getPermanent(source.getSourceId()) == null) {
                    discard();
                }
            }
            return true;
        } else {
            discard();
        }
        return false;
    }

}
