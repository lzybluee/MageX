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

package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class WallOfReverence extends CardImpl {

    public WallOfReverence (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.WALL);

        this.power = new MageInt(1);
        this.toughness = new MageInt(6);
        this.addAbility(DefenderAbility.getInstance());
        this.addAbility(FlyingAbility.getInstance());
        Ability ability = new BeginningOfYourEndStepTriggeredAbility(new WallOfReverenceTriggeredEffect(), true);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    public WallOfReverence (final WallOfReverence card) {
        super(card);
    }

    @Override
    public WallOfReverence copy() {
        return new WallOfReverence(this);
    }
}

class WallOfReverenceTriggeredEffect extends OneShotEffect {
    WallOfReverenceTriggeredEffect() {
        super(Outcome.GainLife);
        staticText = "gain life equal to the power of target creature you control";
    }

    WallOfReverenceTriggeredEffect(WallOfReverenceTriggeredEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        if (target != null && player != null) {
            player.gainLife(target.getPower().getValue(), game, source);
            return true;
        }
        return false;
    }

    @Override
    public WallOfReverenceTriggeredEffect copy() {
        return new WallOfReverenceTriggeredEffect(this);
    }

}