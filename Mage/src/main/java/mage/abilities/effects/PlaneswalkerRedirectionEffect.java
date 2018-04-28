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
package mage.abilities.effects;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 * NOTE: This is no longer used, but I'm leaving it in because why not
 * -TheElk801
 *
 * @author BetaSteward_at_googlemail.com
 */
@Deprecated
public class PlaneswalkerRedirectionEffect extends RedirectionEffect {

    public PlaneswalkerRedirectionEffect() {
        super(Duration.EndOfGame);
    }

    public PlaneswalkerRedirectionEffect(final PlaneswalkerRedirectionEffect effect) {
        super(effect);
    }

    @Override
    public PlaneswalkerRedirectionEffect copy() {
        return new PlaneswalkerRedirectionEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        DamageEvent damageEvent = (DamageEvent) event;
        UUID playerId = getSourceControllerId(event.getSourceId(), game);
        if (!damageEvent.isCombatDamage() && game.getOpponents(event.getTargetId()).contains(playerId)) {
            Player target = game.getPlayer(event.getTargetId());
            Player player = game.getPlayer(playerId);
            if (target != null && player != null) {
                int numPlaneswalkers = game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_PLANESWALKER, target.getId(), game);
                if (numPlaneswalkers > 0 && player.chooseUse(outcome, "Redirect damage to planeswalker?", source, game)) {
                    redirectTarget = new TargetPermanent(StaticFilters.FILTER_PERMANENT_PLANESWALKER);
                    if (numPlaneswalkers == 1) {
                        List<Permanent> planeswalker = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_PLANESWALKER, target.getId(), game);
                        if (!planeswalker.isEmpty()) {
                            redirectTarget.add(planeswalker.get(0).getId(), game);
                        }
                    } else {
                        player.choose(Outcome.Damage, redirectTarget, null, game);
                    }
                    if (!game.isSimulation()) {
                        Permanent redirectTo = game.getPermanent(redirectTarget.getFirstTarget());
                        if (redirectTo != null) {
                            game.informPlayers(player.getLogName() + " redirects " + event.getAmount() + " damage to " + redirectTo.getLogName());
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private UUID getSourceControllerId(UUID sourceId, Game game) {
        StackObject source = game.getStack().getStackObject(sourceId);
        if (source != null) {
            return source.getControllerId();
        }
        Permanent permanent = game.getBattlefield().getPermanent(sourceId);
        if (permanent != null) {
            return permanent.getControllerId();
        }
        // for effects like Deflecting Palm (could be wrong if card was played multiple times by different players)
        return game.getContinuousEffects().getControllerOfSourceId(sourceId);
    }
}
