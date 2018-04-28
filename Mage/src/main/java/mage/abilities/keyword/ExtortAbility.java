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

package mage.abilities.keyword;

import java.util.UUID;

import mage.constants.Zone;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * FAQ 2013/01/11
 *
 * 702.99. Extort
 *
 * 702.99a Extort is a triggered ability. "Extort" means "Whenever you cast a spell,
 * you may pay White or Black Mana. If you do, each opponent loses 1 life and you gain
 * life equal to the total life lost this way."
 *
 * 702.99b If a permanent has multiple instances of extort, each triggers separately.
 *
 * @author LevelX2
 */
public class ExtortAbility extends TriggeredAbilityImpl {

    public ExtortAbility() {
        super(Zone.BATTLEFIELD, new ExtortEffect(), false);
    }

    public ExtortAbility(final ExtortAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId());
    }

    @Override
    public String getRule() {
        return "Extort <i>(Whenever you cast a spell, you may pay {WB}. If you do, each opponent loses 1 life and you gain that much life.)</i>";
    }

    @Override
    public ExtortAbility copy() {
        return new ExtortAbility(this);
    }
}

class ExtortEffect extends OneShotEffect {
    public ExtortEffect() {
        super(Outcome.Damage);
        staticText = "each opponent loses 1 life and you gain that much life";
    }

    public ExtortEffect(final ExtortEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            if (player.chooseUse(Outcome.Damage, new StringBuilder("Extort opponents? (").append(permanent.getName()).append(')').toString(), source, game)) {
                Cost cost = new ManaCostsImpl("{W/B}");
                if (cost.pay(source, game, source.getSourceId(), player.getId(), false, null)) {
                    int loseLife = 0;
                    for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                        loseLife += game.getPlayer(opponentId).loseLife(1, game, false);
                    }
                    if (loseLife > 0) {
                        game.getPlayer(source.getControllerId()).gainLife(loseLife, game, source);
                    }
                    if (!game.isSimulation())
                        game.informPlayers(new StringBuilder(permanent.getName()).append(" extorted opponents ").append(loseLife).append(" life").toString());
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public ExtortEffect copy() {
        return new ExtortEffect(this);
    }
}
