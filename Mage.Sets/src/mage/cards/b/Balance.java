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

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetControlledPermanent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author emerald000
 */
public class Balance extends CardImpl {

    public Balance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{W}");

        // Each player chooses a number of lands he or she controls equal to the number of lands controlled by the player who controls the fewest, then sacrifices the rest. Players discard cards and sacrifice creatures the same way.
        this.getSpellAbility().addEffect(new BalanceEffect());
    }

    public Balance(final Balance card) {
        super(card);
    }

    @Override
    public Balance copy() {
        return new Balance(this);
    }
}

class BalanceEffect extends OneShotEffect {

    BalanceEffect() {
        super(Outcome.Sacrifice);
        staticText = "Each player chooses a number of lands he or she controls equal to the number of lands controlled by the player who controls the fewest, then sacrifices the rest. Players sacrifice creatures and discard cards the same way";
    }

    BalanceEffect(final BalanceEffect effect) {
        super(effect);
    }

    @Override
    public BalanceEffect copy() {
        return new BalanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            //Lands
            int minLand = Integer.MAX_VALUE;
            Cards landsToSacrifice = new CardsImpl();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int count = game.getBattlefield().countAll(new FilterControlledLandPermanent(), player.getId(), game);
                    if (count < minLand) {
                        minLand = count;
                    }
                }
            }

            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    TargetControlledPermanent target = new TargetControlledPermanent(minLand, minLand, new FilterControlledLandPermanent("lands to keep"), true);
                    if (target.choose(Outcome.Sacrifice, player.getId(), source.getSourceId(), game)) {
                        for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterControlledLandPermanent(), player.getId(), source.getSourceId(), game)) {
                            if (permanent != null && !target.getTargets().contains(permanent.getId())) {
                                landsToSacrifice.add(permanent);
                            }
                        }
                    }
                }
            }

            for (UUID cardId : landsToSacrifice) {
                Permanent permanent = game.getPermanent(cardId);
                if (permanent != null) {
                    permanent.sacrifice(source.getSourceId(), game);
                }
            }

            //Creatures
            int minCreature = Integer.MAX_VALUE;
            Cards creaturesToSacrifice = new CardsImpl();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int count = game.getBattlefield().countAll(new FilterControlledCreaturePermanent(), player.getId(), game);
                    if (count < minCreature) {
                        minCreature = count;
                    }
                }
            }

            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    TargetControlledPermanent target = new TargetControlledPermanent(minCreature, minCreature, new FilterControlledCreaturePermanent("creatures to keep"), true);
                    if (target.choose(Outcome.Sacrifice, player.getId(), source.getSourceId(), game)) {
                        for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), player.getId(), source.getSourceId(), game)) {
                            if (permanent != null && !target.getTargets().contains(permanent.getId())) {
                                creaturesToSacrifice.add(permanent);
                            }
                        }
                    }
                }
            }

            for (UUID cardId : creaturesToSacrifice) {
                Permanent permanent = game.getPermanent(cardId);
                if (permanent != null) {
                    permanent.sacrifice(source.getSourceId(), game);
                }
            }

            //Cards in hand
            int minCard = Integer.MAX_VALUE;
            Map<UUID, Cards> cardsToDiscard = new HashMap<>(2);
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int count = player.getHand().size();
                    if (count < minCard) {
                        minCard = count;
                    }
                }
            }

            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    Cards cards = new CardsImpl();
                    TargetCardInHand target = new TargetCardInHand(minCard, new FilterCard("cards to keep"));
                    if (target.choose(Outcome.Discard, player.getId(), source.getSourceId(), game)) {
                        for (Card card : player.getHand().getCards(game)) {
                            if (card != null && !target.getTargets().contains(card.getId())) {
                                cards.add(card);
                            }
                        }
                        cardsToDiscard.put(playerId, cards);
                    }
                }
            }

            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null && cardsToDiscard.get(playerId) != null) {
                    for (UUID cardId : cardsToDiscard.get(playerId)) {
                        Card card = game.getCard(cardId);
                        if (card != null) {
                            player.discard(card, source, game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
