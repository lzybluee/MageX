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
package mage.cards.g;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author North
 */
public final class GravePact extends CardImpl {

    public GravePact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}{B}{B}");


        // Whenever a creature you control dies, each other player sacrifices a creature.
        this.addAbility(new GravePactTriggeredAbility());
    }

    public GravePact(final GravePact card) {
        super(card);
    }

    @Override
    public GravePact copy() {
        return new GravePact(this);
    }
}

class GravePactTriggeredAbility extends TriggeredAbilityImpl {

    public GravePactTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GravePactEffect());
    }

    public GravePactTriggeredAbility(final GravePactTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GravePactTriggeredAbility copy() {
        return new GravePactTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD
                && ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD) {
            Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (permanent.getControllerId().equals(this.getControllerId()) && permanent.isCreature()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control dies, " + super.getRule();
    }
}

class GravePactEffect extends OneShotEffect {

    public GravePactEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "each other player sacrifices a creature";
    }

    public GravePactEffect(final GravePactEffect effect) {
        super(effect);
    }

    @Override
    public GravePactEffect copy() {
        return new GravePactEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<UUID> perms = new ArrayList<>();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {            
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null && !playerId.equals(source.getControllerId())) {
                    TargetControlledCreaturePermanent target = new TargetControlledCreaturePermanent();
                    target.setNotTarget(true);
                    if (target.canChoose(player.getId(), game)) {
                        player.chooseTarget(Outcome.Sacrifice, target, source, game);
                        perms.addAll(target.getTargets());
                    }
                }
            }
            for (UUID permID : perms) {
                Permanent permanent = game.getPermanent(permID);
                if (permanent != null) {
                    permanent.sacrifice(source.getSourceId(), game);
                }
            }
            return true;        
        }
        return false;        
    }
}
