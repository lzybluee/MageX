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
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInASingleGraveyard;

/**
 *
 * @author LevelX2
 */
public final class SereneRemembrance extends CardImpl {

    public SereneRemembrance (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{G}");


        // Shuffle Serene Remembrance and up to three target cards from a single graveyard into their owners' libraries.
        this.getSpellAbility().addEffect(new SereneRemembranceEffect());
        this.getSpellAbility().addTarget(new TargetCardInASingleGraveyard(0,3,new FilterCard("up to three target cards from a single graveyard")));
        
    }

    public SereneRemembrance(final SereneRemembrance card) {
        super(card);
    }

    @Override
    public SereneRemembrance  copy() {
        return new SereneRemembrance(this);
    }
}

class SereneRemembranceEffect extends OneShotEffect {
    
    public SereneRemembranceEffect() {
        super(Outcome.Benefit);
        this.staticText = "Shuffle Serene Remembrance and up to three target cards from a single graveyard into their owners' libraries";
    }
    
    public SereneRemembranceEffect(final SereneRemembranceEffect effect) {
        super(effect);
    }
    
    @Override
    public SereneRemembranceEffect copy() {
        return new SereneRemembranceEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        Player graveyardPlayer = null;
        for (UUID cardInGraveyard : targetPointer.getTargets(game, source)) {
            Card card = game.getCard(cardInGraveyard);
            if (card != null) {
                for (Player player : game.getPlayers().values()) {
                    if (player.getGraveyard().contains(card.getId())) {
                        graveyardPlayer = player;
                        player.getGraveyard().remove(card);
                        result |= card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                    }
                }
            }            
        }
        Card card = game.getCard(source.getSourceId());
        result |= card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, false);
        Player player = game.getPlayer(card.getOwnerId());
        if (player != null){
            player.shuffleLibrary(source, game);
        }
        if (graveyardPlayer != null && !graveyardPlayer.equals(player)) {
            graveyardPlayer.shuffleLibrary(source, game);
        }
        return result;
    }
}
