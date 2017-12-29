/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 * @author nantuko
 */
public class PlayWithTheTopCardRevealedEffect extends ContinuousEffectImpl {

    protected boolean allPlayers;

    public PlayWithTheTopCardRevealedEffect() {
        this(false);
    }

    public PlayWithTheTopCardRevealedEffect(boolean allPlayers) {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Detriment);
        this.allPlayers = allPlayers;
        if (allPlayers) {
            staticText = "Players play with the top card of their libraries revealed.";
        } else {
            staticText = "Play with the top card of your library revealed";
        }
    }

    public PlayWithTheTopCardRevealedEffect(final PlayWithTheTopCardRevealedEffect effect) {
        super(effect);
        this.allPlayers = effect.allPlayers;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (allPlayers) {
                for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null && !isCastFromPlayersLibrary(game, playerId)) {
                        player.setTopCardRevealed(true);
                    }
                }
            } else if (!isCastFromPlayersLibrary(game, controller.getId())) {
                controller.setTopCardRevealed(true);
            }
            return true;
        }
        return false;
    }

    boolean isCastFromPlayersLibrary(Game game, UUID playerId) {
        if (!game.getStack().isEmpty()) {
            StackObject stackObject = game.getStack().getLast();
            return stackObject instanceof Spell
                    && !((Spell) stackObject).isDoneActivatingManaAbilities()
                    && Zone.LIBRARY.equals(((Spell) stackObject).getFromZone());
        }
        return false;
    }

    @Override
    public PlayWithTheTopCardRevealedEffect copy() {
        return new PlayWithTheTopCardRevealedEffect(this);
    }

}
