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
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetDiscard;

/**
 *
 * @author LevelX2
 */
public final class BlastOfGenius extends CardImpl {

    public BlastOfGenius(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{R}");

        // Choose any target. Draw three cards and discard a card. Blast of Genius deals damage equal to the converted mana cost of the discard card to that creature or player.
        this.getSpellAbility().addEffect(new BlastOfGeniusEffect());
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    public BlastOfGenius(final BlastOfGenius card) {
        super(card);
    }

    @Override
    public BlastOfGenius copy() {
        return new BlastOfGenius(this);
    }

}

class BlastOfGeniusEffect extends OneShotEffect {

    public BlastOfGeniusEffect() {
        super(Outcome.Benefit);
        this.staticText = "Choose any target. Draw three cards and discard a card. Blast of Genius deals damage equal to the converted mana cost of the discard card to that permanent or player";
    }

    public BlastOfGeniusEffect(final BlastOfGeniusEffect effect) {
        super(effect);
    }

    @Override
    public BlastOfGeniusEffect copy() {
        return new BlastOfGeniusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.drawCards(3, game);
            TargetDiscard target = new TargetDiscard(player.getId());
            if (target.canChoose(source.getSourceId(), player.getId(), game)) {
                player.choose(Outcome.Discard, target, source.getSourceId(), game);
                Card card = player.getHand().get(target.getFirstTarget(), game);
                if (card != null) {
                    player.discard(card, source, game);
                    int damage = card.getConvertedManaCost();
                    Permanent creature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
                    if (creature != null) {
                        creature.damage(damage, source.getSourceId(), game, false, true);
                        return true;
                    }
                    Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
                    if (targetPlayer != null) {
                        targetPlayer.damage(damage, source.getSourceId(), game, false, true);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
