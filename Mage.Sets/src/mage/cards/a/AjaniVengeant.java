/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.cards.a;

import java.util.UUID;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyAllControlledTargetEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class AjaniVengeant extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("lands");

    static {
        filter.add(new CardTypePredicate(CardType.LAND));
    }

    public AjaniVengeant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.PLANESWALKER},"{2}{R}{W}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.AJANI);

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(3));

        // +1: Target permanent doesn't untap during its controller's next untap step.
        LoyaltyAbility ability1 = new LoyaltyAbility(new DontUntapInControllersNextUntapStepTargetEffect(), 1);
        ability1.addTarget(new TargetPermanent());
        this.addAbility(ability1);

        // −2: Ajani Vengeant deals 3 damage to any target and you gain 3 life.
        Effects effects1 = new Effects();
        effects1.add(new DamageTargetEffect(3));
        effects1.add(new GainLifeEffect(3));
        LoyaltyAbility ability2 = new LoyaltyAbility(effects1, -2);
        ability2.addTarget(new TargetAnyTarget());
        this.addAbility(ability2);

        // −7: Destroy all lands target player controls.
        LoyaltyAbility ability3 = new LoyaltyAbility(new DestroyAllControlledTargetEffect(filter), -7);
        ability3.addTarget(new TargetPlayer());
        this.addAbility(ability3);

    }

    public AjaniVengeant(final AjaniVengeant card) {
        super(card);
    }

    @Override
    public AjaniVengeant copy() {
        return new AjaniVengeant(this);
    }
}
