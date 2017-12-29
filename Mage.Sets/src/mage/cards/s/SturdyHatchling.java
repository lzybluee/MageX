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
import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 * @author Loki
 */
public class SturdyHatchling extends CardImpl {

    private static final FilterSpell filterGreenSpell = new FilterSpell("a green spell");
    private static final FilterSpell filterBlueSpell = new FilterSpell("a blue spell");

    static {
        filterGreenSpell.add(new ColorPredicate(ObjectColor.GREEN));
        filterBlueSpell.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public SturdyHatchling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G/U}");
        this.subtype.add(SubType.ELEMENTAL);


        this.power = new MageInt(6);
        this.toughness = new MageInt(6);
        
        // Sturdy Hatchling enters the battlefield with four -1/-1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.M1M1.createInstance(4)),"with four -1/-1 counters on it"));
        // {G/U}: Sturdy Hatchling gains shroud until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(ShroudAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl("{G/U}")));
        // Whenever you cast a green spell, remove a -1/-1 counter from Sturdy Hatchling.
        this.addAbility(new SpellCastControllerTriggeredAbility(new RemoveCounterSourceEffect(CounterType.M1M1.createInstance(1)), filterGreenSpell, false));
        // Whenever you cast a blue spell, remove a -1/-1 counter from Sturdy Hatchling.
        this.addAbility(new SpellCastControllerTriggeredAbility(new RemoveCounterSourceEffect(CounterType.M1M1.createInstance(1)), filterBlueSpell, false));
    }

    public SturdyHatchling(final SturdyHatchling card) {
        super(card);
    }

    @Override
    public SturdyHatchling copy() {
        return new SturdyHatchling(this);
    }
}
