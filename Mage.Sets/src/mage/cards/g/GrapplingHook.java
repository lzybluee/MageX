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
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class GrapplingHook extends CardImpl {

    public GrapplingHook(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has double strike.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(DoubleStrikeAbility.getInstance(), AttachmentType.EQUIPMENT)));
        // Whenever equipped creature attacks, you may have target creature block it this turn if able.
        Ability ability = new AttacksAttachedTriggeredAbility(new GrapplingHookEffect(), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        // Equip {4}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(4)));
    }

    public GrapplingHook(final GrapplingHook card) {
        super(card);
    }

    @Override
    public GrapplingHook copy() {
        return new GrapplingHook(this);
    }
}

class GrapplingHookEffect extends RequirementEffect {

    public GrapplingHookEffect() {
        this(Duration.EndOfTurn);
    }

    public GrapplingHookEffect(Duration duration) {
        super(duration);
        staticText = "target creature block it this turn if able";
    }

    public GrapplingHookEffect(final GrapplingHookEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getFirstTarget())) {
            Permanent equipment = game.getPermanent(source.getSourceId());
            if (equipment != null && equipment.getAttachedTo() != null) {
                return permanent.canBlock(equipment.getAttachedTo(), game);
            }
        }
        return false;
    }

    @Override
    public boolean mustAttack(Game game) {
        return false;
    }

    @Override
    public boolean mustBlock(Game game) {
        return true;
    }

    @Override
    public UUID mustBlockAttacker(Ability source, Game game) {
        Permanent attachment = game.getPermanent(source.getSourceId());
        return attachment != null ? attachment.getAttachedTo() : null;
    }

    @Override
    public GrapplingHookEffect copy() {
        return new GrapplingHookEffect(this);
    }
}
