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
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 * @author Loki
 */
public class ArchonOfRedemption extends CardImpl {

    public ArchonOfRedemption(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.subtype.add(SubType.ARCHON);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());
        // Whenever Archon of Redemption or another creature with flying enters the battlefield under your control, you may gain life equal to that creature's power.
        this.addAbility(new ArchonOfRedemptionTriggeredAbility());
    }

    public ArchonOfRedemption(final ArchonOfRedemption card) {
        super(card);
    }

    @Override
    public ArchonOfRedemption copy() {
        return new ArchonOfRedemption(this);
    }
}

class ArchonOfRedemptionTriggeredAbility extends TriggeredAbilityImpl {

    ArchonOfRedemptionTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, true);
    }

    ArchonOfRedemptionTriggeredAbility(final ArchonOfRedemptionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ArchonOfRedemptionTriggeredAbility copy() {
        return new ArchonOfRedemptionTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent.getControllerId().equals(getControllerId())
                && permanent.isCreature()
                && (permanent.getId().equals(getSourceId())
                || (permanent.getAbilities().contains(FlyingAbility.getInstance())))) {
            this.getEffects().clear();
            this.addEffect(new GainLifeEffect(permanent.getPower().getValue()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} or another creature with flying enters the battlefield under your control, you may gain life equal to that creature's power.";
    }
}
