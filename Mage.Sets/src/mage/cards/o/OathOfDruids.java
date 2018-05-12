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
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPlayer;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author Plopman
 */
public class OathOfDruids extends CardImpl {

    private final UUID originalId;
    private static final FilterPlayer filter = new FilterPlayer();

    static {
        filter.add(new OathOfDruidsPredicate());
    }

    public OathOfDruids(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // At the beginning of each player's upkeep, that player chooses target player who controls more creatures than he or she does and is their opponent.
        // The first player may reveal cards from the top of their library until he or she reveals a creature card.
        // If he or she does, that player puts that card onto the battlefield and all other cards revealed this way into their graveyard.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new OathOfDruidsEffect(), TargetController.ANY, false);
        ability.addTarget(new TargetPlayer(1, 1, false, filter));
        originalId = ability.getOriginalId();
        this.addAbility(ability);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getOriginalId().equals(originalId)) {
            Player activePlayer = game.getPlayer(game.getActivePlayerId());
            if (activePlayer != null) {
                ability.getTargets().clear();
                TargetPlayer target = new TargetPlayer(1, 1, false, filter);
                target.setTargetController(activePlayer.getId());
                ability.getTargets().add(target);
            }
        }
    }

    public OathOfDruids(final OathOfDruids card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public OathOfDruids copy() {
        return new OathOfDruids(this);
    }
}

class OathOfDruidsPredicate implements ObjectSourcePlayerPredicate<ObjectSourcePlayer<Player>> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    @Override
    public boolean apply(ObjectSourcePlayer<Player> input, Game game) {
        Player targetPlayer = input.getObject();
        //Get active input.playerId because adjust target is used after canTarget function
        UUID activePlayerId = game.getActivePlayerId();
        if (targetPlayer == null || activePlayerId == null) {
            return false;
        }
        if (!targetPlayer.hasOpponent(activePlayerId, game)) {
            return false;
        }
        int countTargetPlayer = game.getBattlefield().countAll(filter, targetPlayer.getId(), game);
        int countActivePlayer = game.getBattlefield().countAll(filter, activePlayerId, game);

        return countTargetPlayer > countActivePlayer;
    }

    @Override
    public String toString() {
        return "player who controls more creatures than he or she does and is their opponent";
    }
}

class OathOfDruidsEffect extends OneShotEffect {

    public OathOfDruidsEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "that player chooses target player who controls more creatures than he or she does and is their opponent. "
                + "The first player may reveal cards from the top of their library until he or she reveals a creature card. "
                + "If he or she does, that player puts that card onto the battlefield and all other cards revealed this way into their graveyard";
    }

    public OathOfDruidsEffect(OathOfDruidsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(game.getActivePlayerId());
        if (controller == null) {
            return false;
        }
        Cards revealed = new CardsImpl();
        Card selectedCard = null;
        Cards notSelectedCards = new CardsImpl();
        if (!controller.chooseUse(Outcome.Benefit, "Use this ability?", source, game)) {
            return true;
        }
        //The first player may reveal cards from the top of their library
        for (Card card : controller.getLibrary().getCards(game)) {
            revealed.add(card);
            // until he or she reveals a creature card.
            if (card.isCreature()) {
                selectedCard = card;
                break;
            } else {
                notSelectedCards.add(card);
            }
        }
        controller.revealCards(source, revealed, game);

        //If he or she does, that player puts that card onto the battlefield
        if (selectedCard != null) {
            controller.moveCards(selectedCard, Zone.BATTLEFIELD, source, game);
        }
        // and all other cards revealed this way into their graveyard
        controller.moveCards(notSelectedCards, Zone.GRAVEYARD, source, game);
        return true;
    }

    @Override
    public OathOfDruidsEffect copy() {
        return new OathOfDruidsEffect(this);
    }
}
