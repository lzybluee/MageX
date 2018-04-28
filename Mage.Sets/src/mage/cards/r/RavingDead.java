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
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.AttackIfAbleTargetRandomOpponentSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class RavingDead extends CardImpl {

    public RavingDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(6);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
        // At the beginning of combat on your turn, choose an opponent at random. Raving Dead attacks that player this combat if able.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new AttackIfAbleTargetRandomOpponentSourceEffect(), TargetController.YOU, false));
        // Whenever Raving Dead deals combat damage to a player, that player loses half their life, rounded down.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new RavingDeadDamageEffect(), false, true));
    }

    public RavingDead(final RavingDead card) {
        super(card);
    }

    @Override
    public RavingDead copy() {
        return new RavingDead(this);
    }
}

class RavingDeadDamageEffect extends OneShotEffect {

    public RavingDeadDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "that player loses half their life, rounded down";
    }

    public RavingDeadDamageEffect(final RavingDeadDamageEffect effect) {
        super(effect);
    }

    @Override
    public RavingDeadDamageEffect copy() {
        return new RavingDeadDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player != null) {
            Integer amount = player.getLife() / 2;
            if (amount > 0) {
                player.loseLife(amount, game, false);
            }
            return true;
        }
        return false;
    }
}
