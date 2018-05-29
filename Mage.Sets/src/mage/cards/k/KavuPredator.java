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
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class KavuPredator extends CardImpl {

    public KavuPredator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.KAVU);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Whenever an opponent gains life, put that many +1/+1 counters on Kavu Predator.
        this.addAbility(new KavuPredatorTriggeredAbility());
    }

    public KavuPredator(final KavuPredator card) {
        super(card);
    }

    @Override
    public KavuPredator copy() {
        return new KavuPredator(this);
    }
}

class KavuPredatorTriggeredAbility extends TriggeredAbilityImpl {

    public KavuPredatorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new KavuPredatorEffect());
    }

    public KavuPredatorTriggeredAbility(final KavuPredatorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KavuPredatorTriggeredAbility copy() {
        return new KavuPredatorTriggeredAbility(this);
    }


    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.GAINED_LIFE;
    }
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(this.controllerId).contains(event.getPlayerId())) {
            this.getEffects().get(0).setValue("gainedLife", new Integer(event.getAmount()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent gains life, " + super.getRule();
    }
}

class KavuPredatorEffect extends OneShotEffect {

    public KavuPredatorEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "put that many +1/+1 counters on {this}";
    }

    public KavuPredatorEffect(final KavuPredatorEffect effect) {
        super(effect);
    }

    @Override
    public KavuPredatorEffect copy() {
        return new KavuPredatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            Integer gainedLife  = (Integer) this.getValue("gainedLife");
            if (gainedLife != null) {
                permanent.addCounters(CounterType.P1P1.createInstance(gainedLife.intValue()), source, game);
                Player player = game.getPlayer(source.getControllerId());
                if (player != null) {
                    game.informPlayers(new StringBuilder(player.getLogName()).append(" puts ").append(gainedLife).append(" +1/+1 counter on ").append(permanent.getName()).toString());
                }
            }
            return true;
        }
        return false;
    }
}
