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
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeAllCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class Soulblast extends CardImpl {

    public Soulblast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}{R}{R}");

        // As an additional cost to cast Soulblast, sacrifice all creatures you control.
        this.getSpellAbility().addCost(new SacrificeAllCost(StaticFilters.FILTER_PERMANENT_CREATURES_CONTROLLED));

        // Soulblast deals damage to any target equal to the total power of the sacrificed creatures.
        this.getSpellAbility().addEffect(new SoulblastEffect());
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    public Soulblast(final Soulblast card) {
        super(card);
    }

    @Override
    public Soulblast copy() {
        return new Soulblast(this);
    }
}

class SoulblastEffect extends OneShotEffect {

    public SoulblastEffect() {
        super(Outcome.Benefit);
        this.staticText = "Soulblast deals damage to any target equal to the total power of the sacrificed creatures";
    }

    public SoulblastEffect(final SoulblastEffect effect) {
        super(effect);
    }

    @Override
    public SoulblastEffect copy() {
        return new SoulblastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int power = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeAllCost) {
                for (Permanent permanent : ((SacrificeAllCost) cost).getPermanents()) {
                    power += permanent.getPower().getValue();
                }
            }
        }
        if (power > 0) {
            Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
            if (player != null) {
                player.damage(power, source.getSourceId(), game, false, true);
            } else {
                Permanent creature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
                if (creature != null) {
                    creature.damage(power, source.getSourceId(), game, false, true);
                }
            }
        }
        return true;
    }
}
