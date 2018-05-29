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
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.EchoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Plopman
 */
public final class ThranWeaponry extends CardImpl {

    public ThranWeaponry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // Echo {4}
        this.addAbility(new EchoAbility("{4}"));
        // You may choose not to untap Thran Weaponry during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());

        // {2}, {tap}: All creatures get +2/+2 for as long as Thran Weaponry remains tapped.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ThranWeaponryEffect(), new ManaCostsImpl("{2}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        
    }

    public ThranWeaponry(final ThranWeaponry card) {
        super(card);
    }

    @Override
    public ThranWeaponry copy() {
        return new ThranWeaponry(this);
    }
}

class ThranWeaponryEffect extends BoostAllEffect{

    public ThranWeaponryEffect() {
        super(2, 2, Duration.WhileOnBattlefield);
        staticText = "All creatures get +2/+2 for as long as Thran Weaponry remains tapped";
    }

    public ThranWeaponryEffect(final ThranWeaponryEffect effect) {
        super(effect);
    }

    @Override
    public ThranWeaponryEffect copy() {
        return new ThranWeaponryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent ThranWeaponry = game.getPermanent(source.getSourceId());
        if (ThranWeaponry != null) {
            if (ThranWeaponry.isTapped()) {
                super.apply(game, source);
                return true;
            } else {
                used = true;
            }
        }
        return false;
    }
}
