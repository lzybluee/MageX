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
package mage.cards.j;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public class JinxedRing extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a nontoken permanent");

    static {
        filter.add(Predicates.not(new TokenPredicate()));
    }

    public JinxedRing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Whenever a nontoken permanent is put into your graveyard from the battlefield, Jinxed Ring deals 1 damage to you.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(new DamageControllerEffect(1), false, filter, false, true));

        // Sacrifice a creature: Target opponent gains control of Jinxed Ring.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new JinxedRingEffect(),
                new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public JinxedRing(final JinxedRing card) {
        super(card);
    }

    @Override
    public JinxedRing copy() {
        return new JinxedRing(this);
    }
}

class JinxedRingEffect extends ContinuousEffectImpl {

    public JinxedRingEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        staticText = "Target opponent gains control of {this}";
    }

    public JinxedRingEffect(final JinxedRingEffect effect) {
        super(effect);
    }

    @Override
    public JinxedRingEffect copy() {
        return new JinxedRingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) source.getSourceObjectIfItStillExists(game);
        if (permanent != null) {
            return permanent.changeControllerId(source.getFirstTarget(), game);
        } else {
            discard();
        }
        return false;
    }

}
