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

import java.util.Optional;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.effects.common.combat.CantAttackBlockAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J (based on jeffwadsworth)
 */
public final class SerraBestiary extends CardImpl {

    public SerraBestiary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Removal));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // At the beginning of your upkeep, sacrifice Serra Bestiary unless you pay {W}{W}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ManaCostsImpl("{W}{W}")), TargetController.YOU, false));

        // Enchanted creature can't attack or block, and its activated abilities with {T} in their costs can't be activated.
        Ability ability2 = new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackBlockAttachedEffect(AttachmentType.AURA));
        ability2.addEffect(new SerraBestiaryRuleModifyingEffect());
        this.addAbility(ability2);

    }

    public SerraBestiary(final SerraBestiary card) {
        super(card);
    }

    @Override
    public SerraBestiary copy() {
        return new SerraBestiary(this);
    }
}

class SerraBestiaryRuleModifyingEffect extends ContinuousRuleModifyingEffectImpl {

    public SerraBestiaryRuleModifyingEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = ", and its activated abilities with {T} in their costs can't be activated";
    }

    public SerraBestiaryRuleModifyingEffect(final SerraBestiaryRuleModifyingEffect effect) {
        super(effect);
    }

    @Override
    public SerraBestiaryRuleModifyingEffect copy() {
        return new SerraBestiaryRuleModifyingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATE_ABILITY;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent enchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (enchantment == null) {
            return false;
        }
        Permanent enchantedCreature = game.getPermanent(enchantment.getAttachedTo());
        if (enchantedCreature == null) {
            return false;
        }
        MageObject object = game.getObject(event.getSourceId());
        Optional<Ability> ability = game.getAbility(event.getTargetId(), event.getSourceId());
        if (ability.isPresent()
                && object != null
                && object.isCreature()
                && object.getId().equals(enchantedCreature.getId())
                && game.getState().getPlayersInRange(source.getControllerId(), game).contains(event.getPlayerId())) {
            if (ability.get().getCosts().stream().anyMatch((cost) -> (cost instanceof TapSourceCost))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        return "Enchanted creature can't use its activated abilities that use {tap} in their costs.";
    }
}
