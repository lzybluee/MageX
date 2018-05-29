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
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.UntapEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author anonymous
 */
public final class InstillEnergy extends CardImpl {

    public InstillEnergy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // Enchanted creature can attack as though it had haste.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CanAttackAsThoughItHadHasteEnchantedEffect(Duration.WhileOnBattlefield)));
        // {0}: Untap enchanted creature. Activate this ability only during your turn and only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new UntapEnchantedEffect(), new GenericManaCost(0), 1, MyTurnCondition.instance));
    }

    public InstillEnergy(final InstillEnergy card) {
        super(card);
    }

    @Override
    public InstillEnergy copy() {
        return new InstillEnergy(this);
    }
}

class CanAttackAsThoughItHadHasteEnchantedEffect extends AsThoughEffectImpl {

    public CanAttackAsThoughItHadHasteEnchantedEffect(Duration duration) {
        super(AsThoughEffectType.ATTACK_AS_HASTE, duration, Outcome.Benefit);
        staticText = "Enchanted creature can attack as though it had haste";
    }

    public CanAttackAsThoughItHadHasteEnchantedEffect(final CanAttackAsThoughItHadHasteEnchantedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CanAttackAsThoughItHadHasteEnchantedEffect copy() {
        return new CanAttackAsThoughItHadHasteEnchantedEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        return enchantment != null && enchantment.getAttachedTo() != null && enchantment.getAttachedTo().equals(objectId);
    }
}
