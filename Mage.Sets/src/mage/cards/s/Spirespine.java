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
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.BlocksIfAbleAttachedEffect;
import mage.abilities.effects.common.combat.BlocksIfAbleSourceEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.BestowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public class Spirespine extends CardImpl {

    public Spirespine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Bestow 4G (If you cast this card for its bestow cost, it's an Aura spell with enchant creature. It becomes a creature again if it's not attached to a creature.)
        this.addAbility(new BestowAbility(this, "{4}{G}"));
        // Spirespine blocks each turn if able.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BlocksIfAbleSourceEffect(Duration.WhileOnBattlefield)));
        // Enchanted creature gets +4/+1 and blocks each combat if able.
        Effect effect = new BoostEnchantedEffect(4, 1, Duration.WhileOnBattlefield);
        effect.setText("Enchanted creature gets +4/+1");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new BlocksIfAbleAttachedEffect(Duration.WhileOnBattlefield, AttachmentType.AURA);
        effect.setText("and blocks each combat if able");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public Spirespine(final Spirespine card) {
        super(card);
    }

    @Override
    public Spirespine copy() {
        return new Spirespine(this);
    }
}
