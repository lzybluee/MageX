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
package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.List;
import java.util.UUID;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesAttachedEffect;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author North
 */
public final class BlazingTorch extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Vampires or Zombies");
    static {
        filter.add(Predicates.or(new SubtypePredicate(SubType.VAMPIRE), 
                                 new SubtypePredicate(SubType.ZOMBIE)));
    }
    
    public BlazingTorch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature can't be blocked by Vampires or Zombies. (!this is a static ability of the equipment)
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new CantBeBlockedByCreaturesAttachedEffect(Duration.WhileOnBattlefield, filter, AttachmentType.EQUIPMENT)));

        
        // Equipped creature has "{tap}, Sacrifice Blazing Torch: Blazing Torch deals 2 damage to any target.")
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BlazingTorchDamageEffect(), new TapSourceCost());
        ability.addCost(new BlazingTorchCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(ability, AttachmentType.EQUIPMENT)));
        
        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(1)));
    }

    public BlazingTorch(final BlazingTorch card) {
        super(card);
    }

    @Override
    public BlazingTorch copy() {
        return new BlazingTorch(this);
    }
}

class BlazingTorchCost extends CostImpl {

    public BlazingTorchCost() {
        this.text = "Sacrifice Blazing Torch";
    }

    public BlazingTorchCost(BlazingTorchCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent permanent = game.getPermanent(ability.getSourceId());
        List<UUID> attachments = permanent.getAttachments();
        for (UUID attachmentId : attachments) {
            Permanent attachment = game.getPermanent(attachmentId);
            if (attachment != null && attachment.getName().equals("Blazing Torch")) {
                ((BlazingTorchDamageEffect) ability.getEffects().get(0)).setSourceId(attachmentId);
                paid |= attachment.sacrifice(sourceId, game);
                return paid;
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public BlazingTorchCost copy() {
        return new BlazingTorchCost(this);
    }
}

class BlazingTorchDamageEffect extends OneShotEffect {

    private UUID sourceId;

    public BlazingTorchDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "Blazing Torch deals 2 damage to any target";
    }

    public BlazingTorchDamageEffect(final BlazingTorchDamageEffect effect) {
        super(effect);
    }

    @Override
    public BlazingTorchDamageEffect copy() {
        return new BlazingTorchDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent != null && sourceId != null) {
            permanent.damage(2, sourceId, game, false, true);
            return true;
        }
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null && sourceId != null) {
            player.damage(2, sourceId, game, false, true);
            return true;
        }
        return false;
    }

    public void setSourceId(UUID sourceId) {
        this.sourceId = sourceId;
    }
}
