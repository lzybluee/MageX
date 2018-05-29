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
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class FracturingGust extends CardImpl {

    public FracturingGust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G/W}{G/W}{G/W}");

        // Destroy all artifacts and enchantments. You gain 2 life for each permanent destroyed this way.
        this.getSpellAbility().addEffect(new FracturingGustDestroyEffect());
    }

    public FracturingGust(final FracturingGust card) {
        super(card);
    }

    @Override
    public FracturingGust copy() {
        return new FracturingGust(this);
    }
}
class FracturingGustDestroyEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();
    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.ARTIFACT),
                                 new CardTypePredicate(CardType.ENCHANTMENT)));
    }
    public FracturingGustDestroyEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy all artifacts and enchantments. You gain 2 life for each permanent destroyed this way";
    }

    public FracturingGustDestroyEffect(final FracturingGustDestroyEffect effect) {
        super(effect);
    }

    @Override
    public FracturingGustDestroyEffect copy() {
        return new FracturingGustDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int destroyedPermanents = 0;
            for (Permanent permanent: game.getState().getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                if (permanent.destroy(source.getSourceId(), game, false)) {
                    ++destroyedPermanents;
                }
            }
            game.applyEffects(); // needed in case a destroyed permanent did prevent life gain
            if (destroyedPermanents > 0) {
                controller.gainLife(2 * destroyedPermanents, game, source);
            }
            return true;
        }
        return false;
    }
}
