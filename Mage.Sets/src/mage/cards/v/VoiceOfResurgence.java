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
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.VoiceOfResurgenceToken;
import mage.game.stack.Spell;

/**
 *
 * @author jeffwadsworth
 */
public class VoiceOfResurgence extends CardImpl {

    public VoiceOfResurgence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever an opponent casts a spell during your turn or when Voice of Resurgence dies, create a green and white Elemental creature token with "This creature's power and toughness are each equal to the number of creatures you control."
        this.addAbility(new VoiceOfResurgenceTriggeredAbility());

    }

    public VoiceOfResurgence(final VoiceOfResurgence card) {
        super(card);
    }

    @Override
    public VoiceOfResurgence copy() {
        return new VoiceOfResurgence(this);
    }
}

class VoiceOfResurgenceTriggeredAbility extends TriggeredAbilityImpl {

    public VoiceOfResurgenceTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new VoiceOfResurgenceToken()), false);
        setLeavesTheBattlefieldTrigger(true);
    }

    public VoiceOfResurgenceTriggeredAbility(final VoiceOfResurgenceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST || event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // Opponent casts spell during your turn
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null
                    && game.getOpponents(super.getControllerId()).contains(spell.getControllerId())
                    && game.getActivePlayerId().equals(super.getControllerId())) {
                return true;
            }
        }
        // Voice of Resurgence Dies
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && getSourceId().equals(event.getTargetId())) {
            ZoneChangeEvent zce = (ZoneChangeEvent) event;
            return zce.getFromZone() == Zone.BATTLEFIELD && zce.getToZone() == Zone.GRAVEYARD;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts a spell during your turn or when {this} dies, create a green and white Elemental creature token with \"This creature's power and toughness are each equal to the number of creatures you control.";
    }

    @Override
    public VoiceOfResurgenceTriggeredAbility copy() {
        return new VoiceOfResurgenceTriggeredAbility(this);
    }
}
