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
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.filter.predicate.permanent.UnblockedPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetCardInOpponentsGraveyard;

/**
 *
 * @author LevelX2
 */
public final class Skullsnatcher extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("unblocked attacker you control");

    static {
        filter.add(new UnblockedPredicate());
    }

    public Skullsnatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.NINJA);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Ninjutsu {B} ({B}, Return an unblocked attacker you control to hand: Put this card onto the battlefield from your hand tapped and attacking.)
        this.addAbility(new NinjutsuAbility(new ManaCostsImpl("{B")));
        
        // Whenever Skullsnatcher deals combat damage to a player, exile up to two target cards from that player's graveyard.
        Effect effect = new ExileTargetEffect(null, "", Zone.GRAVEYARD);
        effect.setText("exile up to two target cards from that player's graveyard");
        this.addAbility(new SkullsnatcherTriggeredAbility(effect));
    }

    public Skullsnatcher(final Skullsnatcher card) {
        super(card);
    }

    @Override
    public Skullsnatcher copy() {
        return new Skullsnatcher(this);
    }
}

class SkullsnatcherTriggeredAbility extends TriggeredAbilityImpl {

    SkullsnatcherTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
    }

    SkullsnatcherTriggeredAbility(final SkullsnatcherTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SkullsnatcherTriggeredAbility copy() {
        return new SkullsnatcherTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((DamagedPlayerEvent) event).isCombatDamage()
                && event.getSourceId().equals(sourceId)) {

            FilterCard filter = new FilterCard("up to two target cards from that player's graveyard");
            filter.add(new OwnerIdPredicate(event.getPlayerId()));
            filter.setMessage("up to two cards in " + game.getPlayer(event.getTargetId()).getLogName() + "'s graveyard");
            this.getTargets().clear();
            this.addTarget(new TargetCardInOpponentsGraveyard(0,2,filter));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, " + super.getRule();
   }
}
