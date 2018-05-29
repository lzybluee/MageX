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
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.token.BeastToken;
import mage.players.Player;

/**
 *
 * @author wetterlicht
 */
public final class PulseOfTheTangle extends CardImpl {

    public PulseOfTheTangle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}{G}");

        // Create a 3/3 green Beast creature token. Then if an opponent controls more creatures than you, return Pulse of the Tangle to its owner's hand.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new BeastToken()));
        this.getSpellAbility().addEffect(new PulseOfTheTangleReturnToHandEffect());
    }

    public PulseOfTheTangle(final PulseOfTheTangle card) {
        super(card);
    }

    @Override
    public PulseOfTheTangle copy() {
        return new PulseOfTheTangle(this);
    }
}

class PulseOfTheTangleReturnToHandEffect extends OneShotEffect {

    PulseOfTheTangleReturnToHandEffect() {
        super(Outcome.Benefit);
        this.staticText = "Then if an opponent controls more creatures than you, return {this} to its owner's hand";
    }

    PulseOfTheTangleReturnToHandEffect(final PulseOfTheTangleReturnToHandEffect effect) {
        super(effect);
    }

    @Override
    public PulseOfTheTangleReturnToHandEffect copy() {
        return new PulseOfTheTangleReturnToHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        FilterControlledCreaturePermanent controllerFilter = new FilterControlledCreaturePermanent();
        PermanentsOnBattlefieldCount controllerCount = new PermanentsOnBattlefieldCount(controllerFilter);
        int controllerAmount = controllerCount.calculate(game, source, this);
        boolean check = false;
        if (controller != null) {
            for (UUID opponentID : game.getOpponents(controller.getId())) {
                if (opponentID != null) {
                    FilterCreaturePermanent opponentFilter = new FilterCreaturePermanent();
                    opponentFilter.add(new ControllerIdPredicate(opponentID));
                    PermanentsOnBattlefieldCount opponentCreatureCount = new PermanentsOnBattlefieldCount(opponentFilter);
                    check = opponentCreatureCount.calculate(game, source, this) > controllerAmount;
                    if (check) {
                        break;
                    }
                }
            }
            if (check) {
                Card card = game.getCard(source.getSourceId());
                controller.moveCards(card, Zone.HAND, source, game);
                return true;
            }
        }
        return false;
    }
}
