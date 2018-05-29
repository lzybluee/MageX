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
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanentWithDifferentTypes;

/**
 *
 * @author LevelX2
 */
public final class RivalsDuel extends CardImpl {

    public RivalsDuel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Choose two target creatures that share no creature types. Those creatures fight each other.
        this.getSpellAbility().addEffect(new RivalsDuelFightTargetsEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanentWithDifferentTypes(2, 2, StaticFilters.FILTER_PERMANENT_CREATURE, false));

    }

    public RivalsDuel(final RivalsDuel card) {
        super(card);
    }

    @Override
    public RivalsDuel copy() {
        return new RivalsDuel(this);
    }
}

class RivalsDuelFightTargetsEffect extends OneShotEffect {

    public RivalsDuelFightTargetsEffect() {
        super(Outcome.Damage);
        staticText = "Choose two target creatures that share no creature types. Those creatures fight each other";
    }

    public RivalsDuelFightTargetsEffect(final RivalsDuelFightTargetsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature1 = game.getPermanent(source.getFirstTarget());
        Permanent creature2 = game.getPermanent(source.getTargets().get(0).getTargets().get(1));
        // 20110930 - 701.10
        if (creature1 != null
                && creature2 != null) {
            creature1.damage(creature2.getPower().getValue(), creature2.getId(), game, false, true);
            creature2.damage(creature1.getPower().getValue(), creature1.getId(), game, false, true);
            return true;
        }
        return false;
    }

    @Override
    public RivalsDuelFightTargetsEffect copy() {
        return new RivalsDuelFightTargetsEffect(this);
    }
}
