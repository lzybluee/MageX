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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public class Chromanticore extends CardImpl {

    public Chromanticore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{W}{U}{B}{R}{G}");
        this.subtype.add(SubType.MANTICORE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Bestow {2}{W}{U}{B}{R}{G}
        this.addAbility(new BestowAbility(this, "{2}{W}{U}{B}{R}{G}"));
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
        // Enchanted creature gets +4/+4 and has flying, first strike, vigilance, trample and lifelink.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(4,4));
        Effect effect = new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.AURA);
        effect.setText("and has flying");
        ability.addEffect(effect);
        effect = new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.AURA);
        effect.setText(", first strike");
        ability.addEffect(effect);
        effect = new GainAbilityAttachedEffect(VigilanceAbility.getInstance(), AttachmentType.AURA);
        effect.setText(", vigilance");
        ability.addEffect(effect);
        effect = new GainAbilityAttachedEffect(TrampleAbility.getInstance(), AttachmentType.AURA);
        effect.setText(", trample");
        ability.addEffect(effect);
        effect = new GainAbilityAttachedEffect(LifelinkAbility.getInstance(), AttachmentType.AURA);
        effect.setText("and lifelink");
        ability.addEffect(effect);
        this.addAbility(ability);        
        
    }

    public Chromanticore(final Chromanticore card) {
        super(card);
    }

    @Override
    public Chromanticore copy() {
        return new Chromanticore(this);
    }
}
