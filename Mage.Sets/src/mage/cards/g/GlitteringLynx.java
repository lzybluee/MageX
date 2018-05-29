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
public final class GlitteringLynx extends CardImpl {

    public GlitteringLynx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Prevent all damage that would be dealt to Glittering Lynx.
        this.addAbility(GlitteringLynxAbility.getInstance());
        // {2}: Until end of turn, Glittering Lynx loses "Prevent all damage that would be dealt to Glittering Lynx." Any player may activate this ability.
        SimpleActivatedAbility ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseAbilitySourceEffect(GlitteringLynxAbility.getInstance(), Duration.EndOfTurn).setText("Until end of turn, {this} loses \"Prevent all damage that would be dealt to {this}.\""), new ManaCostsImpl("{2}"));
        ability2.setMayActivate(TargetController.ANY);
        ability2.addEffect(new InfoEffect("Any player may activate this ability"));
        this.addAbility(ability2);
    }

    public GlitteringLynx(final GlitteringLynx card) {
        super(card);
    }

    @Override
    public GlitteringLynx copy() {
        return new GlitteringLynx(this);
    }

}

class GlitteringLynxAbility extends StaticAbility {

    private static final GlitteringLynxAbility instance =  new GlitteringLynxAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static GlitteringLynxAbility getInstance() {
        return instance;
    }

    public GlitteringLynxAbility() {
        super(Zone.BATTLEFIELD, new PreventAllDamageToSourceEffect(Duration.WhileOnBattlefield));
    }

    @Override
    public GlitteringLynxAbility copy() {
        return instance;
    }

}
