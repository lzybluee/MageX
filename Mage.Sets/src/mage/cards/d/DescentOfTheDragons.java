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

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.DragonToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public class DescentOfTheDragons extends CardImpl {

    public DescentOfTheDragons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}{R}");

        // Destroy any number of target creatures.  For each creature destroyed this way, its controller creates a 4/4 red Dragon creature token with flying.
        this.getSpellAbility().addEffect(new DescentOfTheDragonsEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE));

    }

    public DescentOfTheDragons(final DescentOfTheDragons card) {
        super(card);
    }

    @Override
    public DescentOfTheDragons copy() {
        return new DescentOfTheDragons(this);
    }
}

class DescentOfTheDragonsEffect extends OneShotEffect {

    public DescentOfTheDragonsEffect() {
        super(Outcome.Benefit);
        staticText = "Destroy any number of target creatures. For each creature destroyed this way, its controller creates a 4/4 red Dragon creature token with flying";
    }

    public DescentOfTheDragonsEffect(final DescentOfTheDragonsEffect effect) {
        super(effect);
    }

    @Override
    public DescentOfTheDragonsEffect copy() {
        return new DescentOfTheDragonsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Map<UUID, Integer> playersWithTargets = new HashMap<>();
            for (Target target : source.getTargets()) {
                for (UUID permanentId : target.getTargets()) {
                    Permanent permanent = game.getPermanent(permanentId);
                    if (permanent != null) {
                        UUID controllerOfTargetId = permanent.getControllerId();
                        if (permanent.destroy(source.getSourceId(), game, false)) {
                            int count = playersWithTargets.getOrDefault(controllerOfTargetId, 0);
                            playersWithTargets.put(controllerOfTargetId, count + 1);

                        }
                    }
                }
            }
            DragonToken dragonToken = new DragonToken();
            for (UUID playerId : playersWithTargets.keySet()) {
                dragonToken.putOntoBattlefield(playersWithTargets.get(playerId), game, source.getSourceId(), playerId);
            }
            return true;
        }
        return false;
    }
}
