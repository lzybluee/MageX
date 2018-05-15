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

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.permanent.token.GoldForgeGarrisonGolemToken;

/**
 *
 * @author LevelX2
 */
public class GoldForgeGarrison extends CardImpl {

    public GoldForgeGarrison(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.nightCard = true;

        // <i>(Transforms from Golden Guardian.)</i>
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new InfoEffect("<i>(Transforms from Golden Guardian.)</i>"));
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // {T}: Add two mana of any one color.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(2), new TapSourceCost()));

        // {4}, {T}: Create a 4/4 colorless Golem artifact creature token.
        ability = new SimpleActivatedAbility(new CreateTokenEffect(new GoldForgeGarrisonGolemToken(), 1), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    public GoldForgeGarrison(final GoldForgeGarrison card) {
        super(card);
    }

    @Override
    public GoldForgeGarrison copy() {
        return new GoldForgeGarrison(this);
    }
}
