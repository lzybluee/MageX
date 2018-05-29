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

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class BanefulOmen extends CardImpl {

    public BanefulOmen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{B}{B}{B}");


        // At the beginning of your end step, you may reveal the top card of your library. If you do, each opponent loses life equal to that card's converted mana cost.
        this.addAbility(new BanefulOmenTriggeredAbility());
    }

    public BanefulOmen(final BanefulOmen card) {
        super(card);
    }

    @Override
    public BanefulOmen copy() {
        return new BanefulOmen(this);
    }

    class BanefulOmenTriggeredAbility extends TriggeredAbilityImpl {

        public BanefulOmenTriggeredAbility() {
            super(Zone.BATTLEFIELD, new BanefulOmenEffect(), true);
        }

        public BanefulOmenTriggeredAbility(BanefulOmenTriggeredAbility ability) {
            super(ability);
        }

        @Override
        public BanefulOmenTriggeredAbility copy() {
            return new BanefulOmenTriggeredAbility(this);
        }

        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            return event.getType() == EventType.END_TURN_STEP_PRE;
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            return event.getPlayerId().equals(this.controllerId);
        }

        @Override
        public String getRule() {
            return "At the beginning of your end step, you may reveal the top card of your library. If you do, each opponent loses life equal to that card's converted mana cost.";
        }
    }

    static class BanefulOmenEffect extends OneShotEffect {

        public BanefulOmenEffect() {
            super(Outcome.Benefit);
        }

        public BanefulOmenEffect(final BanefulOmenEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player player = game.getPlayer(source.getControllerId());
            if (player == null) {
                return false;
            }
            if (player.getLibrary().hasCards()) {
                Card card = player.getLibrary().getFromTop(game);
                Cards cards = new CardsImpl();
                cards.add(card);
                player.revealCards("Baneful Omen", cards, game);

                if (card != null) {
                    int loseLife = card.getConvertedManaCost();
                    Set<UUID> opponents = game.getOpponents(source.getControllerId());
                    for (UUID opponentUuid : opponents) {
                        Player opponent = game.getPlayer(opponentUuid);
                        if (opponent != null) {
                            opponent.loseLife(loseLife, game, false);
                        }
                    }
                }
            }
            return false;
        }

        @Override
        public BanefulOmenEffect copy() {
            return new BanefulOmenEffect(this);
        }
    }
}
