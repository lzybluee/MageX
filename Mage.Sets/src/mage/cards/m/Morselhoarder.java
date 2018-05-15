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
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author jeffwadsworth
 */
public class Morselhoarder extends CardImpl {

    public Morselhoarder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R/G}{R/G}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // Morselhoarder enters the battlefield with two -1/-1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.M1M1.createInstance(2), false)));
        
        // Remove a -1/-1 counter from Morselhoarder: Add one mana of any color.
        this.addAbility(new MorselhoarderAbility());
        
    }

    public Morselhoarder(final Morselhoarder card) {
        super(card);
    }

    @Override
    public Morselhoarder copy() {
        return new Morselhoarder(this);
    }
}

class MorselhoarderAbility extends ActivatedManaAbilityImpl {
    public MorselhoarderAbility() {
        this(new RemoveCountersSourceCost(CounterType.M1M1.createInstance()));
    }

    public MorselhoarderAbility(Cost cost) {
        super(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(), cost);
        this.netMana.add(new Mana(0,0,0,0,0,0,1, 0));
    }

    public MorselhoarderAbility(final MorselhoarderAbility ability) {
        super(ability);
    }

    @Override
    public MorselhoarderAbility copy() {
        return new MorselhoarderAbility(this);
    }
}
