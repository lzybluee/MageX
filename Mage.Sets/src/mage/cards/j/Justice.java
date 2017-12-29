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
package mage.cards.j;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class Justice extends CardImpl {

    public Justice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}{W}");


        // At the beginning of your upkeep, sacrifice Justice unless you pay {W}{W}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new SacrificeSourceUnlessPaysEffect(new ManaCostsImpl("{W}{W}")), TargetController.YOU, false));
        
        // Whenever a red creature or spell deals damage, Justice deals that much damage to that creature's or spell's controller.
        this.addAbility(new JusticeTriggeredAbility(new JusticeEffect()));
    }

    public Justice(final Justice card) {
        super(card);
    }

    @Override
    public Justice copy() {
        return new Justice(this);
    }
}

class JusticeTriggeredAbility extends TriggeredAbilityImpl {

    public JusticeTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    public JusticeTriggeredAbility(final JusticeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public JusticeTriggeredAbility copy() {
        return new JusticeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_CREATURE
                || event.getType() == EventType.DAMAGED_PLAYER
                || event.getType() == EventType.DAMAGED_PLANESWALKER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        MageObject sourceObject = game.getObject(event.getSourceId());
        if (sourceObject.getColor(game).isRed()) {
            if (sourceObject instanceof Permanent && sourceObject.isCreature()
                    || sourceObject instanceof Spell) {
                this.getEffects().get(0).setValue("damageAmount", event.getAmount());
                this.getEffects().get(0).setTargetPointer(new FixedTarget(game.getControllerId(sourceObject.getId())));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a red creature or spell deals damage, {this} deals that much damage to that creature's or spell's controller.";
    }
}

class JusticeEffect extends OneShotEffect {

    public JusticeEffect() {
        super(Outcome.Damage);
    }

    public JusticeEffect(final JusticeEffect effect) {
        super(effect);
    }

    @Override
    public JusticeEffect copy() {
        return new JusticeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Integer damageAmount = (Integer) this.getValue("damageAmount");
        UUID targetId = this.targetPointer.getFirst(game, source);
        if (damageAmount != null && targetId != null) {
            Player player = game.getPlayer(targetId);
            if (player != null) {
                    player.damage(damageAmount, targetId, game, false, true);
                    return true;
            }
        }
        return false;
    }
}
