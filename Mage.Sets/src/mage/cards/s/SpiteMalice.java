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
import mage.ObjectColor;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetSpell;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author FenrisulfrX
 */
public class SpiteMalice extends SplitCard {

    private static final FilterSpell filterNonCreatureSpell = new FilterSpell("noncreature spell");

    static {
        filterNonCreatureSpell.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    private static final FilterCreaturePermanent filterNonBlackCreature = new FilterCreaturePermanent("nonblack creature");

    static {
        filterNonBlackCreature.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public SpiteMalice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}", "{3}{B}", SpellAbilityType.SPLIT);

        // Spite
        // Counter target noncreature spell.
        this.getLeftHalfCard().getSpellAbility().addEffect(new CounterTargetEffect());
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetSpell(filterNonCreatureSpell));

        // Malice
        // Destroy target nonblack creature. It can't be regenerated.
        this.getRightHalfCard().getSpellAbility().addEffect(new DestroyTargetEffect(true));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent(filterNonBlackCreature));
    }

    public SpiteMalice(final SpiteMalice card) {
        super(card);
    }

    @Override
    public SpiteMalice copy() {
        return new SpiteMalice(this);
    }
}
