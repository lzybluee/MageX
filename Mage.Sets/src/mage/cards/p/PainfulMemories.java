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
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Quercitron
 */
public final class PainfulMemories extends CardImpl {

    public PainfulMemories(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}");


        // Look at target opponent's hand and choose a card from it. Put that card on top of that player's library.
        this.getSpellAbility().addEffect(new PainfulMemoriesEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public PainfulMemories(final PainfulMemories card) {
        super(card);
    }

    @Override
    public PainfulMemories copy() {
        return new PainfulMemories(this);
    }
}

class PainfulMemoriesEffect extends OneShotEffect {

    public PainfulMemoriesEffect() {
        super(Outcome.Discard);
        this.staticText = "Look at target opponent's hand and choose a card from it. Put that card on top of that player's library.";
    }
    
    public PainfulMemoriesEffect(final PainfulMemoriesEffect effect) {
        super(effect);
    }

    @Override
    public PainfulMemoriesEffect copy() {
        return new PainfulMemoriesEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player you = game.getPlayer(source.getControllerId());
        if (targetPlayer != null && you != null) {
            targetPlayer.revealCards("Painful Memories", targetPlayer.getHand(), game);
            
            if (!targetPlayer.getHand().isEmpty()) {
                TargetCard target = new TargetCard(Zone.HAND, new FilterCard());
                if (you.choose(Outcome.Benefit, targetPlayer.getHand(), target, game)) {
                    Card card = targetPlayer.getHand().get(target.getFirstTarget(), game);
                    if (card != null) {
                        return targetPlayer.moveCardToLibraryWithInfo(card, source.getSourceId(), game, Zone.HAND, true, true);
                    }
                }
            }
            return true;
        }
        return false;
    }
    
}
