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
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author cbt33
 */
public class ZombieCannibal extends CardImpl {

    public ZombieCannibal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Zombie Cannibal deals combat damage to a player, you may exile target card from that player's graveyard.
    this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new ZombieCannibalEffect(), true, true));
    
    }

    public ZombieCannibal(final ZombieCannibal card) {
        super(card);
    }

    @Override
    public ZombieCannibal copy() {
        return new ZombieCannibal(this);
    }
}

class ZombieCannibalEffect extends OneShotEffect {
    
    public ZombieCannibalEffect() {
        super(Outcome.Exile);
        staticText = "you may exile target card from that player's graveyard.";
    }
    
    public ZombieCannibalEffect(final ZombieCannibalEffect effect) {
        super(effect);
    }
    
    @Override
    public ZombieCannibalEffect copy() {
        return new ZombieCannibalEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        FilterCard filter = new FilterCard();
        Player player = game.getPlayer(source.getTargets().getFirstTarget());
        if (player != null) {
            filter.add(new OwnerIdPredicate(player.getId()));
            Target target = new TargetCardInGraveyard(filter);
            game.getPermanent(target.getFirstTarget()).moveToExile(null, null, source.getSourceId(), game);
        }
        return false;
    }
    
}
