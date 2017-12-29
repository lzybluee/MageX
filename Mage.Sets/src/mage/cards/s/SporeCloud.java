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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.abilities.effects.common.TapAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.BlockingPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;

/**
 *
 * @author L_J
 */
public class SporeCloud extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("blocking creatures");
    static {
        filter.add(new BlockingPredicate());
    }

    public SporeCloud(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}{G}");

        // Tap all blocking creatures.
        this.getSpellAbility().addEffect(new TapAllEffect(filter));
        // Prevent all combat damage that would be dealt this turn.
        this.getSpellAbility().addEffect(new PreventAllDamageByAllPermanentsEffect(Duration.EndOfTurn, true));
        // Each attacking creature and each blocking creature doesn't untap during its controller's next untap step.
        this.getSpellAbility().addEffect(new SporeCloudEffect());
    }

    public SporeCloud(final SporeCloud card) {
        super(card);
    }

    @Override
    public SporeCloud copy() {
        return new SporeCloud(this);
    }
}

class SporeCloudEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Each attacking creature and each blocking creature");
    static {
        filter.add(Predicates.or(new AttackingPredicate(), new BlockingPredicate()));
    }

    public SporeCloudEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each attacking creature and each blocking creature doesn't untap during its controller's next untap step";
    }

    public SporeCloudEffect(final SporeCloudEffect effect) {
        super(effect);
    }

    @Override
    public SporeCloudEffect copy() {
        return new SporeCloudEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            List<Permanent> doNotUntapNextUntapStep = new ArrayList<>();
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                doNotUntapNextUntapStep.add(permanent);
            }
            if (!doNotUntapNextUntapStep.isEmpty()) {
                ContinuousEffect effect = new DontUntapInControllersNextUntapStepTargetEffect("This creature");
                effect.setTargetPointer(new FixedTargets(doNotUntapNextUntapStep, game));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}
