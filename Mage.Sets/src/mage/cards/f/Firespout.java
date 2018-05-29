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
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.watchers.common.ManaSpentToCastWatcher;

/**
 *
 * @author LevelX2
 */
public final class Firespout extends CardImpl {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("creature without flying");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creature with flying");
    static {
        filter1.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
        filter2.add(new AbilityPredicate(FlyingAbility.class));
    }

    public Firespout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R/G}");


        // Firespout deals 3 damage to each creature without flying if {R} was spent to cast Firespout and 3 damage to each creature with flying if {G} was spent to cast it.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageAllEffect(3, filter1),
                new ManaWasSpentCondition(ColoredManaSymbol.R), "{this} deals 3 damage to each creature without flying if {R} was spent to cast {this}"));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageAllEffect(3, filter2),
                new ManaWasSpentCondition(ColoredManaSymbol.G), " And 3 damage to each creature with flying if {G} was spent to cast it"));
        this.getSpellAbility().addEffect(new InfoEffect("<i>(Do both if {R}{G} was spent.)</i>"));
        this.getSpellAbility().addWatcher(new ManaSpentToCastWatcher());



    }

    public Firespout(final Firespout card) {
        super(card);
    }

    @Override
    public Firespout copy() {
        return new Firespout(this);
    }
}
