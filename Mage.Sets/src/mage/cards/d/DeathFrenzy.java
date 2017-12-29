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

import java.util.UUID;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;

/**
 *
 * @author LevelX2
 */
public class DeathFrenzy extends CardImpl {

    public DeathFrenzy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}{G}");


        // All creatures get -2/-2 until end of turn. Whenever a creature dies this turn, you gain 1 life.
        this.getSpellAbility().addEffect(new BoostAllEffect(-2, -2, Duration.EndOfTurn, new FilterCreaturePermanent("All creatures"), false));
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new DeathFrenzyDelayedTriggeredAbility()));
    }

    public DeathFrenzy(final DeathFrenzy card) {
        super(card);
    }

    @Override
    public DeathFrenzy copy() {
        return new DeathFrenzy(this);
    }
}

class DeathFrenzyDelayedTriggeredAbility extends DelayedTriggeredAbility {


    public DeathFrenzyDelayedTriggeredAbility() {
        super(new GainLifeEffect(1), Duration.EndOfTurn, false);
    }

    public DeathFrenzyDelayedTriggeredAbility(DeathFrenzyDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent() && zEvent.getTarget() != null && StaticFilters.FILTER_PERMANENT_CREATURES.match(zEvent.getTarget(), sourceId, controllerId, game)) {
            return true;
        }
        return false;
    }

    @Override
    public DeathFrenzyDelayedTriggeredAbility copy() {
        return new DeathFrenzyDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a creature dies this turn, " + modes.getText();
    }
}
