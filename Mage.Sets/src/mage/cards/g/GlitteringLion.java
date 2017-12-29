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
package mage.cards.g;

import java.io.ObjectStreamException;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.abilities.effects.common.continuous.LoseAbilitySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author L_J
 */
public class GlitteringLion extends CardImpl {

    public GlitteringLion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Prevent all damage that would be dealt to Glittering Lion.
        this.addAbility(GlitteringLionAbility.getInstance());
        // {3}: Until end of turn, Glittering Lion loses "Prevent all damage that would be dealt to Glittering Lion." Any player may activate this ability.
        SimpleActivatedAbility ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseAbilitySourceEffect(GlitteringLionAbility.getInstance(), Duration.EndOfTurn).setText("Until end of turn, {this} loses \"Prevent all damage that would be dealt to {this}.\""), new ManaCostsImpl("{3}"));
        ability2.setMayActivate(TargetController.ANY);
        ability2.addEffect(new InfoEffect("Any player may activate this ability"));
        this.addAbility(ability2);
    }

    public GlitteringLion(final GlitteringLion card) {
        super(card);
    }

    @Override
    public GlitteringLion copy() {
        return new GlitteringLion(this);
    }

}

class GlitteringLionAbility extends StaticAbility {

    private static final GlitteringLionAbility instance =  new GlitteringLionAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static GlitteringLionAbility getInstance() {
        return instance;
    }

    public GlitteringLionAbility() {
        super(Zone.BATTLEFIELD, new PreventAllDamageToSourceEffect(Duration.WhileOnBattlefield));
    }

    @Override
    public GlitteringLionAbility copy() {
        return instance;
    }

}
