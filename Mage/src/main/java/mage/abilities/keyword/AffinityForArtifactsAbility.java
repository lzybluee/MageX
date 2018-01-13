/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.AdjustingSourceCosts;
import mage.abilities.effects.common.AffinityEffect;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * Affinity for artifacts
 */
public class AffinityForArtifactsAbility extends SimpleStaticAbility implements AdjustingSourceCosts {

    public AffinityForArtifactsAbility() {
        super(Zone.OUTSIDE, new AffinityEffect(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT));
        setRuleAtTheTop(true);
    }

    public AffinityForArtifactsAbility(final AffinityForArtifactsAbility ability) {
        super(ability);
    }

    @Override
    public SimpleStaticAbility copy() {
        return new AffinityForArtifactsAbility(this);
    }

    @Override
    public String getRule() {
        return "affinity for artifacts <i>(This spell costs {1} less to cast for each artifact you control.)</i>";
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            int count = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT, ability.getControllerId(), game).size();
            if (count > 0) {
                CardUtil.adjustCost((SpellAbility)ability, count);
            }
        }
    }
}
