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
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public class RoarOfReclamation extends CardImpl {

    public RoarOfReclamation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{W}{W}");

        // Each player returns all artifact cards from their graveyard to the battlefield.
        this.getSpellAbility().addEffect(new RoarOfReclamationEffect());
    }

    public RoarOfReclamation(final RoarOfReclamation card) {
        super(card);
    }

    @Override
    public RoarOfReclamation copy() {
        return new RoarOfReclamation(this);
    }
}

class RoarOfReclamationEffect extends OneShotEffect {
    
    public RoarOfReclamationEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "Each player returns all artifact cards from their graveyard to the battlefield";
    }

    public RoarOfReclamationEffect(final RoarOfReclamationEffect effect) {
        super(effect);
    }

    @Override
    public RoarOfReclamationEffect copy() {
        return new RoarOfReclamationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = true;
        for (Player player : game.getPlayers().values()) {
            if (player != null) {
                Cards cards = player.getGraveyard();
                for (Card card : cards.getCards(new FilterArtifactCard(), game)) {
                    if (card != null) {
                        if (!card.putOntoBattlefield(game, Zone.GRAVEYARD, source.getSourceId(), card.getOwnerId(), false)) {
                            result = false;
                        }
                    }
                }
            }
        }
        return result;
    }
}