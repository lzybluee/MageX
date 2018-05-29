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
package mage.cards.t;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author jeffwadsworth
 */
public final class TalarasBane extends CardImpl {

    public TalarasBane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Target opponent reveals their hand. You choose a green or white creature card from it. You gain life equal that creature card's toughness, then that player discards that card.
        this.getSpellAbility().addEffect(new TalarasBaneEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

    }

    public TalarasBane(final TalarasBane card) {
        super(card);
    }

    @Override
    public TalarasBane copy() {
        return new TalarasBane(this);
    }
}

class TalarasBaneEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("a green or white creature card");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.GREEN),
                new ColorPredicate(ObjectColor.WHITE)));
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public TalarasBaneEffect() {
        super(Outcome.Detriment);
        this.staticText = "Target opponent reveals their hand. You choose a green or white creature card from it. You gain life equal to that creature card's toughness, then that player discards that card";
    }

    public TalarasBaneEffect(final TalarasBaneEffect effect) {
        super(effect);
    }

    @Override
    public TalarasBaneEffect copy() {
        return new TalarasBaneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player you = game.getPlayer(source.getControllerId());
        Card card = null;
        if (targetPlayer != null && you != null) {
            targetPlayer.revealCards("Talaras Bane", targetPlayer.getHand(), game);
            TargetCard target = new TargetCard(Zone.HAND, filter);
            if (you.choose(Outcome.Benefit, targetPlayer.getHand(), target, game)) {
                card = targetPlayer.getHand().get(target.getFirstTarget(), game);
            }
            if (card != null) {
                int lifeGain = card.getToughness().getValue();
                you.gainLife(lifeGain, game, source);
                return targetPlayer.discard(card, source, game);
            }
        }
        return false;
    }
}
