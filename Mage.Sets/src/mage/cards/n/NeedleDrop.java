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
package mage.cards.n;

import java.util.UUID;
import mage.MageItem;
import mage.MageObject;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePlayerOrPlaneswalker;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.watchers.common.DamageDoneWatcher;

/**
 *
 * @author Quercitron
 */
public class NeedleDrop extends CardImpl {

    private static final FilterCreaturePlayerOrPlaneswalker FILTER = new FilterCreaturePlayerOrPlaneswalker();

    static {
        FILTER.getCreatureFilter().add(new DamagedThisTurnPredicate());
        FILTER.getPlaneswalkerFilter().add(new DamagedThisTurnPredicate());
        FILTER.getPlayerFilter().add(new DamagedThisTurnPredicate());
    }

    public NeedleDrop(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Needle Drop deals 1 damage to any target that was dealt damage this turn.
        Effect effect = new DamageTargetEffect(1);
        effect.setText("{this} deals 1 damage to any target that was dealt damage this turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetAnyTarget(1, 1, FILTER));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    public NeedleDrop(final NeedleDrop card) {
        super(card);
    }

    @Override
    public NeedleDrop copy() {
        return new NeedleDrop(this);
    }
}

class DamagedThisTurnPredicate implements Predicate<MageItem> {

    @Override
    public boolean apply(MageItem input, Game game) {
        DamageDoneWatcher watcher = (DamageDoneWatcher) game.getState().getWatchers().get(DamageDoneWatcher.class.getSimpleName());
        if (watcher != null) {
            if (input instanceof MageObject) {
                return watcher.isDamaged(input.getId(), ((MageObject) input).getZoneChangeCounter(game), game);
            }
            if (input instanceof Player) {
                return watcher.isDamaged(input.getId(), 0, game);
            }
        }
        return false;
    }

}
