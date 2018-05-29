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
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.SurgedCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.SurgeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;

/**
 *
 * @author fireshoes
 */
public final class RecklessBushwhacker extends CardImpl {

    private final static FilterControlledCreaturePermanent FILTER = new FilterControlledCreaturePermanent("other creatures you control");

    static {
        FILTER.add(new AnotherPredicate());
    }

    public RecklessBushwhacker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Surge {1}{R} (You may cast this spell for its surge cost if you or a teammate has cast another spell this turn)
        addAbility(new SurgeAbility(this, "{1}{R}"));

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Reckless Bushwhacker enters the battlefield, if its surge cost was paid, other creatures you control get +1/+0 and gain haste until end of turn.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new BoostControlledEffect(1, 0, Duration.EndOfTurn, true), false);
        ability.addEffect(new GainAbilityControlledEffect(HasteAbility.getInstance(), Duration.EndOfTurn, FILTER, true));
        this.addAbility(new ConditionalTriggeredAbility(ability, SurgedCondition.instance,
                "When {this} enters the battlefield, if its surge cost was paid, other creatures you control get +1/+0 and gain haste until end of turn."));

    }

    public RecklessBushwhacker(final RecklessBushwhacker card) {
        super(card);
    }

    @Override
    public RecklessBushwhacker copy() {
        return new RecklessBushwhacker(this);
    }
}
