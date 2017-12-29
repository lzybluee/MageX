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

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author spjspj
 */
public class AuramancersGuise extends CardImpl {

    public AuramancersGuise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature gets +2/+2 for each Aura attached to it and has vigilance.
        DynamicValue ptBoost = new EnchantedCreatureAurasCount();
        BoostEnchantedEffect effect = new BoostEnchantedEffect(ptBoost, ptBoost, Duration.WhileOnBattlefield);
        effect.setText("Enchanted creature gets +2/+2 for each Aura attached to it");
        SimpleStaticAbility ability2 = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        ability2.addEffect(new GainAbilityAttachedEffect(VigilanceAbility.getInstance(), AttachmentType.AURA).setText("and has vigilance"));
        this.addAbility(ability2);
    }

    public AuramancersGuise(final AuramancersGuise card) {
        super(card);
    }

    @Override
    public AuramancersGuise copy() {
        return new AuramancersGuise(this);
    }
}

class EnchantedCreatureAurasCount implements DynamicValue {

    public EnchantedCreatureAurasCount() {
    }

    public EnchantedCreatureAurasCount(final EnchantedCreatureAurasCount dynamicValue) {
    }

    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        int count = 0;
        Permanent aura = game.getPermanent(source.getSourceId());
        if (aura != null) {
            Permanent permanent = game.getPermanent(aura.getAttachedTo());
            if (permanent != null) {
                List<UUID> attachments = permanent.getAttachments();
                for (UUID attachmentId : attachments) {
                    Permanent attached = game.getPermanent(attachmentId);
                    if (attached != null && attached.hasSubtype(SubType.AURA, game)) {
                        count++;
                    }

                }
                return 2 * count;
            }
        }
        return count;
    }

    @Override
    public EnchantedCreatureAurasCount copy() {
        return new EnchantedCreatureAurasCount(this);
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "of its auras";
    }

}
