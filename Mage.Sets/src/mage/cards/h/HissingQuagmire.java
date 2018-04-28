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
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;

/**
 *
 * @author fireshoes
 */
public class HissingQuagmire extends CardImpl {

    public HissingQuagmire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Hissing Quagmire enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {B} or {G}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new GreenManaAbility());

        // {1}{B}{G}: Hissing Quagmire becomes a 2/2 black and green Elemental creature with deathtouch until end of turn. It's still a land.
        Effect effect = new BecomesCreatureSourceEffect(new HissingQuagmireToken(), "land", Duration.EndOfTurn);
        effect.setText("{this} becomes a 2/2 black and green Elemental creature with deathtouch until end of turn. It's still a land");
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{1}{B}{G}")));
    }

    public HissingQuagmire(final HissingQuagmire card) {
        super(card);
    }

    @Override
    public HissingQuagmire copy() {
        return new HissingQuagmire(this);
    }
}

class HissingQuagmireToken extends TokenImpl {

    public HissingQuagmireToken() {
        super("", "2/2 black and green Elemental creature with deathtouch");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ELEMENTAL);
        color.setBlack(true);
        color.setGreen(true);
        power = new MageInt(2);
        toughness = new MageInt(2);
        addAbility(DeathtouchAbility.getInstance());
    }
    public HissingQuagmireToken(final HissingQuagmireToken token) {
        super(token);
    }

    public HissingQuagmireToken copy() {
        return new HissingQuagmireToken(this);
    }
}
