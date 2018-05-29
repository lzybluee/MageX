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

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author emerald000
 */
public final class Breakthrough extends CardImpl {

    public Breakthrough(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{U}");


        // Draw four cards,
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(4));
        
        //then choose X cards in your hand and discard the rest.
        this.getSpellAbility().addEffect(new BreakthroughEffect());

    }

    public Breakthrough(final Breakthrough card) {
        super(card);
    }

    @Override
    public Breakthrough copy() {
        return new Breakthrough(this);
    }
}

class BreakthroughEffect extends OneShotEffect {
    
    BreakthroughEffect() {
        super(Outcome.Discard);
        this.staticText = ", then choose X cards in your hand and discard the rest.";
    }
    
    BreakthroughEffect(final BreakthroughEffect effect) {
        super(effect);
    }
    
    @Override
    public BreakthroughEffect copy() {
        return new BreakthroughEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int amountToKeep = source.getManaCostsToPay().getX();
            if (amountToKeep == 0) {
                player.discard(player.getHand().size(), source, game);
            }
            else if (amountToKeep < player.getHand().size()) {
                TargetCardInHand target = new TargetCardInHand(amountToKeep, new FilterCard());
                target.setTargetName("cards to keep");
                target.choose(Outcome.Benefit, player.getId(), source.getSourceId(), game);
                for (Card card : player.getHand().getCards(game)) {
                    if (!target.getTargets().contains(card.getId())) {
                        player.discard(card, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
