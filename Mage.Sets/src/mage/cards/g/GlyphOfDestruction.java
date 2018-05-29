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
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetAtBeginningOfNextEndStepEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.BlockingPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class GlyphOfDestruction extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("blocking Wall you control");

    static {
        filter.add(new SubtypePredicate(SubType.WALL));
        filter.add(new BlockingPredicate());
    }

    public GlyphOfDestruction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");

        // Target blocking Wall you control gets +10/+0 until end of combat. Prevent all damage that would be dealt to it this turn. Destroy it at the beginning of the next end step.
        this.getSpellAbility().addEffect(new BoostTargetEffect(10, 0, Duration.EndOfCombat));
        this.getSpellAbility().addEffect(new PreventDamageToTargetEffect(Duration.EndOfTurn, Integer.MAX_VALUE).setText("Prevent all damage that would be dealt to it this turn"));
        this.getSpellAbility().addEffect(new DestroyTargetAtBeginningOfNextEndStepEffect().setText("Destroy it at the beginning of the next end step"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(filter));
    }

    public GlyphOfDestruction(final GlyphOfDestruction card) {
        super(card);
    }

    @Override
    public GlyphOfDestruction copy() {
        return new GlyphOfDestruction(this);
    }
}
