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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author JRHerlehy
 */
public final class GreenbeltRampager extends CardImpl {

    public GreenbeltRampager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.ELEPHANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Greenbelt Rampager enters the battlefield, pay {E}{E}. If you can't, return Greenbelt Rampager to its owner's hand and you get {E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GreenbeltRampagerEffect(), false));
    }

    public GreenbeltRampager(final GreenbeltRampager card) {
        super(card);
    }

    @Override
    public GreenbeltRampager copy() {
        return new GreenbeltRampager(this);
    }

    private static class GreenbeltRampagerEffect extends OneShotEffect {

        GreenbeltRampagerEffect() {
            super(Outcome.Neutral);
            this.staticText = "pay {E}{E}. If you can't, return {this} to its owner's hand and you get {E}";
        }

        GreenbeltRampagerEffect(final GreenbeltRampagerEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller == null) {
                return false;
            }

            if (!new PayEnergyCost(2).pay(source, game, source.getSourceId(), source.getControllerId(), true)) {
                Permanent sourceObject = source.getSourcePermanentIfItStillExists(game);
                if (sourceObject != null) {
                    controller.moveCards(sourceObject, Zone.HAND, source, game);
                    controller.addCounters(CounterType.ENERGY.createInstance(), game);
                }
            }
            return true;
        }

        @Override
        public GreenbeltRampagerEffect copy() {
            return new GreenbeltRampagerEffect(this);
        }
    }

}
