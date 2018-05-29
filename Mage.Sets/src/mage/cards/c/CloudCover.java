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
package mage.cards.c;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public final class CloudCover extends CardImpl {

    public CloudCover(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}{U}");

        // Whenever another permanent you control becomes the target of a spell or ability an opponent controls, you may return that permanent to its owner's hand.
        this.addAbility(new CloudCoverAbility());
    }

    public CloudCover(final CloudCover card) {
        super(card);
    }

    @Override
    public CloudCover copy() {
        return new CloudCover(this);
    }
}

class CloudCoverAbility extends TriggeredAbilityImpl {

    public CloudCoverAbility() {
        super(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), true);
    }

    public CloudCoverAbility(final CloudCoverAbility ability) {
        super(ability);
    }

    @Override
    public CloudCoverAbility copy() {
        return new CloudCoverAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        Player controller = game.getPlayer(this.getControllerId());
        if (permanent != null
                && permanent.getControllerId().equals(getControllerId())
                && !permanent.getId().equals(this.getSourceId())
                && controller != null
                && controller.hasOpponent(event.getPlayerId(), game)) {
            getEffects().get(0).setTargetPointer(new FixedTarget(permanent, game));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever another permanent you control becomes the target of a spell or ability an opponent controls, you may return that permanent to its owner's hand.";
    }
}
