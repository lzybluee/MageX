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
package mage.cards.e;

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
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author cbt33, jeffwadsworth (Supreme Inquisitor)
 */
public class Extract extends CardImpl {

    public Extract(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{U}");


        // Search target player's library for a card and exile it. Then that player shuffles their library.
        this.getSpellAbility().addEffect(new ExtractEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
   
    }

    public Extract(final Extract card) {
        super(card);
    }

    @Override
    public Extract copy() {
        return new Extract(this);
    }
}

class ExtractEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    public ExtractEffect() {
        super(Outcome.Exile);
        staticText = "Search target player's library for a card and exile it. Then that player shuffles their library.";
    }

    public ExtractEffect(final ExtractEffect effect) {
        super(effect);
    }

    @Override
    public ExtractEffect copy() {
        return new ExtractEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && targetPlayer != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(1, 1, filter);
            if (player.searchLibrary(target, game, targetPlayer.getId())) {
                Card card = targetPlayer.getLibrary().remove(target.getFirstTarget(), game);
                if (card != null) {
                    player.moveCardToExileWithInfo(card, null, null, source.getSourceId(), game, Zone.LIBRARY, true);
                }
            }
            targetPlayer.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
