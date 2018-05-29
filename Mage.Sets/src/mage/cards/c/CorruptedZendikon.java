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
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandAttachedEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class CorruptedZendikon extends CardImpl {

    public CorruptedZendikon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.PutCreatureInPlay));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted land is a 3/3 black Ooze creature. It's still a land.
        Ability ability2 = new SimpleStaticAbility(Zone.BATTLEFIELD,
                new BecomesCreatureAttachedEffect(new CorruptedZendikonOozeToken(),
                        "Enchanted land is a 3/3 black Ooze creature. It's still a land.", Duration.WhileOnBattlefield, BecomesCreatureAttachedEffect.LoseType.COLOR));
        this.addAbility(ability2);

        // When enchanted land dies, return that card to its owner's hand.
        Ability ability3 = new DiesAttachedTriggeredAbility(new ReturnToHandAttachedEffect(), "enchanted land", false);
        this.addAbility(ability3);
    }

    public CorruptedZendikon(final CorruptedZendikon card) {
        super(card);
    }

    @Override
    public CorruptedZendikon copy() {
        return new CorruptedZendikon(this);
    }
}

class CorruptedZendikonOozeToken extends TokenImpl {

    public CorruptedZendikonOozeToken() {
        super("Ooze", "3/3 black Ooze creature");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.OOZE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
    }
    public CorruptedZendikonOozeToken(final CorruptedZendikonOozeToken token) {
        super(token);
    }

    public CorruptedZendikonOozeToken copy() {
        return new CorruptedZendikonOozeToken(this);
    }

}
