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
package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByMoreThanOneAllEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByMoreThanOneSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.CounterPredicate;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class BattlefrontKrushok extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature you control with a +1/+1 counter on it");

    static {
        filter.add(new CounterPredicate(CounterType.P1P1));
    }

    public BattlefrontKrushok(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Battlefront Krushok can't be blocked by more than one creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedByMoreThanOneSourceEffect()));

        // Each creature you control with a +1/+1 counter on it can't be blocked by more than one creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedByMoreThanOneAllEffect(filter)));
    }

    public BattlefrontKrushok(final BattlefrontKrushok card) {
        super(card);
    }

    @Override
    public BattlefrontKrushok copy() {
        return new BattlefrontKrushok(this);
    }
}
