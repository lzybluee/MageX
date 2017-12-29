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
package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class CantAttackYouAllEffect extends RestrictionEffect {

    private final FilterCreaturePermanent filterAttacker;
    private final boolean alsoPlaneswalker;

    public CantAttackYouAllEffect(Duration duration) {
        this(duration, StaticFilters.FILTER_PERMANENT_CREATURES);
    }

    public CantAttackYouAllEffect(Duration duration, FilterCreaturePermanent filter) {
        this(duration, filter, false);
    }

    public CantAttackYouAllEffect(Duration duration, FilterCreaturePermanent filter, boolean alsoPlaneswalker) {
        super(duration, Outcome.Benefit);
        this.filterAttacker = filter;
        this.alsoPlaneswalker = alsoPlaneswalker;
        staticText = filterAttacker.getMessage() + " can't attack you"
                + (alsoPlaneswalker ? " or a planeswalker you control" : "")
                + (duration == Duration.UntilYourNextTurn ? " until your next turn" : "");
    }

    CantAttackYouAllEffect(final CantAttackYouAllEffect effect) {
        super(effect);
        this.filterAttacker = effect.filterAttacker;
        this.alsoPlaneswalker = effect.alsoPlaneswalker;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return filterAttacker.match(permanent, source.getSourceId(), source.getControllerId(), game);
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game) {
        if (alsoPlaneswalker) {
            Permanent planeswalker = game.getPermanent(defenderId);
            if (planeswalker != null) {
                defenderId = planeswalker.getControllerId();
            }
        }
        return !defenderId.equals(source.getControllerId());
    }

    @Override
    public CantAttackYouAllEffect copy() {
        return new CantAttackYouAllEffect(this);
    }
}
