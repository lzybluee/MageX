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
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.RenownedSourceCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.RenownAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterAttackingCreature;

/**
 *
 * @author LevelX2
 */
public final class ConsulsLieutenant extends CardImpl {

    public ConsulsLieutenant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // Renown 1
        this.addAbility(new RenownAbility(1));
        // Whenever Consul's Lieutenant attacks, if it's renowned, other attacking creatures you control get +1/+1 until end of turn.
        this.addAbility(new ConditionalTriggeredAbility(new AttacksTriggeredAbility(
                new BoostControlledEffect(1, 1, Duration.EndOfTurn, new FilterAttackingCreature("other attacking creatures you control"), true), false),
                RenownedSourceCondition.instance,
                "Whenever Consul's Lieutenant attacks, if it's renowned, other attacking creatures you control get +1/+1 until end of turn."));

    }

    public ConsulsLieutenant(final ConsulsLieutenant card) {
        super(card);
    }

    @Override
    public ConsulsLieutenant copy() {
        return new ConsulsLieutenant(this);
    }
}
