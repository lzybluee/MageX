
package mage.cards.n;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class Nullify extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("creature or Aura spell");
    
    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.CREATURE),
                new SubtypePredicate(SubType.AURA)));
    }
    
    public Nullify(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}{U}");


        // Counter target creature or Aura spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    public Nullify(final Nullify card) {
        super(card);
    }

    @Override
    public Nullify copy() {
        return new Nullify(this);
    }
}
