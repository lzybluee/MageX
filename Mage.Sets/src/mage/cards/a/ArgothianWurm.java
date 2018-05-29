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
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.PutOnLibrarySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class ArgothianWurm extends CardImpl {

    public ArgothianWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // When Argothian Wurm enters the battlefield, any player may sacrifice a land. If a player does, put Argothian Wurm on top of its owner's library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ArgothianWurmEffect(), false));
    }

    public ArgothianWurm(final ArgothianWurm card) {
        super(card);
    }

    @Override
    public ArgothianWurm copy() {
        return new ArgothianWurm(this);
    }
}

class ArgothianWurmEffect extends PutOnLibrarySourceEffect {
        
    ArgothianWurmEffect() {
        super(true);
        this.staticText = "any player may sacrifice a land. If a player does, put {this} on top of its owner's library";
    }
    
    ArgothianWurmEffect(final ArgothianWurmEffect effect) {
        super(effect);
    }
    
    @Override
    public ArgothianWurmEffect copy() {
        return new ArgothianWurmEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            boolean costPaid = false;
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Cost cost = new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledLandPermanent()));
                Player player = game.getPlayer(playerId);
                if (player != null
                        && cost.canPay(source, source.getSourceId(), playerId, game)
                        && player.chooseUse(Outcome.Sacrifice, "Sacrifice a land?", source, game)
                        && cost.pay(source, game, source.getSourceId(), playerId, true, null)) {
                    costPaid = true;
                }
            }
            if (costPaid) {
                super.apply(game, source);
            }
            return true;
        }
        return false;
    }
}