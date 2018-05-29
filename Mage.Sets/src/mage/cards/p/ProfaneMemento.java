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
package mage.cards.p;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;

/**
 *
 * @author emerald000
 */
public final class ProfaneMemento extends CardImpl {

    public ProfaneMemento(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        // Whenever a creature card is put into an opponent's graveyard from anywhere, you gain 1 life.
        this.addAbility(new ProfaneMementoTriggeredAbility());
    }

    public ProfaneMemento(final ProfaneMemento card) {
        super(card);
    }

    @Override
    public ProfaneMemento copy() {
        return new ProfaneMemento(this);
    }
}

class ProfaneMementoTriggeredAbility extends TriggeredAbilityImpl {
    
    public ProfaneMementoTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(1), false);
    }
    
    public ProfaneMementoTriggeredAbility(final ProfaneMementoTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public ProfaneMementoTriggeredAbility copy() {
        return new ProfaneMementoTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD) {
            Card card = game.getCard(event.getTargetId());
            if (card != null && card.isCreature() && game.getOpponents(controllerId).contains(card.getOwnerId())) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String getRule() {
        return "Whenever a creature card is put into an opponent's graveyard from anywhere, " + super.getRule();
    }
}
