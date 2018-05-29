/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
* 1. Redistributions of source code must retain the above copyright notice, this list of
* conditions and the following disclaimer.
*
* 2. Redistributions in binary form must reproduce the above copyright notice, this list
* of conditions and the following disclaimer in the documentation and/or other materials
* provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.players.PlayerList;

/**
 *
 * @author anonymous
 */
public final class ArbiterOfKnollridge extends CardImpl {

    public ArbiterOfKnollridge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{W}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // When Arbiter of Knollridge enters the battlefield, each player's life total becomes the highest life total among all players.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ArbiterOfKnollridgeEffect()));
    }

    public ArbiterOfKnollridge(final ArbiterOfKnollridge card) {
        super(card);
    }

    @Override
    public ArbiterOfKnollridge copy() {
        return new ArbiterOfKnollridge(this);
    }
}

class ArbiterOfKnollridgeEffect extends OneShotEffect {
    ArbiterOfKnollridgeEffect() {
        super(Outcome.GainLife);
        staticText = "each player's life total becomes the highest life total among all players";
    }

    ArbiterOfKnollridgeEffect(final ArbiterOfKnollridgeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int maxLife = 0;
        PlayerList playerList = game.getState().getPlayersInRange(source.getControllerId(), game);
        for (UUID pid : playerList) {
            Player p = game.getPlayer(pid);
            if (p != null) {
                if (maxLife < p.getLife()) {
                    maxLife = p.getLife();
                }
            }
        }
        for (UUID pid : playerList) {
            Player p = game.getPlayer(pid);
            if (p != null) {
                p.setLife(maxLife, game, source);
            }
        }
        return true;
    }

    @Override
    public ArbiterOfKnollridgeEffect copy() {
        return new ArbiterOfKnollridgeEffect(this);
    }
}