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
package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetPowerSourceEffect;
import mage.abilities.effects.common.continuous.SetToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author michael.napoleon@gmail.com
 */
public class YavimayaKavu extends CardImpl {
  
    private static final FilterCreaturePermanent filterGreenCreature = new FilterCreaturePermanent("green creatures on the battlefield");
    private static final FilterCreaturePermanent filterRedCreature = new FilterCreaturePermanent("red creatures on the battlefield");
    
    static {
      filterGreenCreature.add(new ColorPredicate(ObjectColor.GREEN));
      filterRedCreature.add(new ColorPredicate(ObjectColor.RED));
    }

    public YavimayaKavu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{G}");
        this.subtype.add(SubType.KAVU);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
        

        // Yavimaya Kavu's power is equal to the number of red creatures on the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerSourceEffect(new PermanentsOnBattlefieldCount(filterRedCreature), Duration.EndOfGame)));
        // Yavimaya Kavu's toughness is equal to the number of green creatures on the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetToughnessSourceEffect(new PermanentsOnBattlefieldCount(filterGreenCreature), Duration.EndOfGame)));
    }

    public YavimayaKavu(final YavimayaKavu card) {
        super(card);
    }

    @Override
    public YavimayaKavu copy() {
        return new YavimayaKavu(this);
    }
}
