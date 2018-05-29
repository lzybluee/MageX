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
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.token.MinionToken2;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class InfernalGenesis extends CardImpl {

    public InfernalGenesis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}{B}");

        // At the beginning of each player's upkeep, that player puts the top card of their library into their graveyard.
        // Then he or she creates X 1/1 black Minion creature tokens, where X is that card's converted mana cost.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new InfernalGenesisEffect(), TargetController.ANY, false));
    }

    public InfernalGenesis(final InfernalGenesis card) {
        super(card);
    }

    @Override
    public InfernalGenesis copy() {
        return new InfernalGenesis(this);
    }
}

class InfernalGenesisEffect extends OneShotEffect {

    InfernalGenesisEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "that player puts the top card of their library into their graveyard. " +
                "Then he or she creates X 1/1 black Minion creature tokens, where X is that card's converted mana cost";
    }

    InfernalGenesisEffect(final InfernalGenesisEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player != null) {
            Card card = player.getLibrary().getFromTop(game);
            if (card != null) {
                if (player.moveCards(card, Zone.GRAVEYARD, source, game)) {
                    int cmc = card.getConvertedManaCost();
                    MinionToken2 token = new MinionToken2();
                    token.putOntoBattlefield(cmc, game, source.getSourceId(), player.getId());
                }
            }
        }
        return true;
    }

    @Override
    public InfernalGenesisEffect copy() {
        return new InfernalGenesisEffect(this);
    }
}
