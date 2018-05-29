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
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Loki
 */
public final class SwordOfWarAndPeace extends CardImpl {

    public SwordOfWarAndPeace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2 and has protection from red and from white.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 2));
        Effect effect = new GainAbilityAttachedEffect(ProtectionAbility.from(ObjectColor.RED, ObjectColor.WHITE), AttachmentType.EQUIPMENT);
        effect.setText("and has protection from red and from white");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Whenever equipped creature deals combat damage to a player, Sword of War and Peace deals damage to that player equal to the number of cards in their hand and you gain 1 life for each card in your hand.
        this.addAbility(new SwordOfWarAndPeaceAbility());

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    public SwordOfWarAndPeace(final SwordOfWarAndPeace card) {
        super(card);
    }

    @Override
    public SwordOfWarAndPeace copy() {
        return new SwordOfWarAndPeace(this);
    }

}

class SwordOfWarAndPeaceAbility extends TriggeredAbilityImpl {

    public SwordOfWarAndPeaceAbility() {
        super(Zone.BATTLEFIELD, new SwordOfWarAndPeaceDamageEffect());
        this.addEffect(new GainLifeEffect(new CardsInControllerHandCount()));
    }

    public SwordOfWarAndPeaceAbility(final SwordOfWarAndPeaceAbility ability) {
        super(ability);
    }

    @Override
    public SwordOfWarAndPeaceAbility copy() {
        return new SwordOfWarAndPeaceAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        Permanent damageSource = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (damageEvent.isCombatDamage() && damageSource != null && damageSource.getAttachments().contains(this.getSourceId())) {
            getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature deals combat damage to a player, {this} deals damage to that player equal to the number of cards in their hand and you gain 1 life for each card in your hand.";
    }
}

class SwordOfWarAndPeaceDamageEffect extends OneShotEffect {

    SwordOfWarAndPeaceDamageEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals damage to that player equal to the number of cards in their hand";
    }

    SwordOfWarAndPeaceDamageEffect(final SwordOfWarAndPeaceDamageEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer != null) {
            targetPlayer.damage(targetPlayer.getHand().size(), source.getSourceId(), game, false, true);
        }
        return true;
    }

    @Override
    public SwordOfWarAndPeaceDamageEffect copy() {
        return new SwordOfWarAndPeaceDamageEffect(this);
    }

}
