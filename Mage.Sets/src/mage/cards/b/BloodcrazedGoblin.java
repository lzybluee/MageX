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

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.BloodthirstWatcher;

/**
 *
 * @author BetaSteward_at_googlemail.com
 * @author North
 */
public final class BloodcrazedGoblin extends CardImpl {

    public BloodcrazedGoblin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN, SubType.BERSERKER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BloodcrazedGoblinEffect()));
    }

    public BloodcrazedGoblin(final BloodcrazedGoblin card) {
        super(card);
    }

    @Override
    public BloodcrazedGoblin copy() {
        return new BloodcrazedGoblin(this);
    }

}

class BloodcrazedGoblinEffect extends RestrictionEffect {

    public BloodcrazedGoblinEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack unless an opponent has been dealt damage this turn";
    }

    public BloodcrazedGoblinEffect(final BloodcrazedGoblinEffect effect) {
        super(effect);
    }

    @Override
    public BloodcrazedGoblinEffect copy() {
        return new BloodcrazedGoblinEffect(this);
    }

    @Override
    public boolean canAttack(Game game) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            BloodthirstWatcher watcher = (BloodthirstWatcher) game.getState().getWatchers().get(BloodthirstWatcher.class.getSimpleName(), source.getControllerId()); // BloodthirstWatcher
            return !watcher.conditionMet();
        }
        return false;
    }
}
