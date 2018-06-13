
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author Plopman
 */
public final class Devastation extends CardImpl {

    
    private static final FilterPermanent filter = new FilterPermanent("creatures and lands");
    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.CREATURE), new CardTypePredicate(CardType.LAND)));
    }
    public Devastation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{R}{R}");


        // Destroy all creatures and lands.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    public Devastation(final Devastation card) {
        super(card);
    }

    @Override
    public Devastation copy() {
        return new Devastation(this);
    }
}
