
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.MayTapOrUntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author anonymous
 */
public final class Twiddle extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("artifact, creature, or land");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.LAND)));
    }

    public Twiddle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // You may tap or untap target artifact, creature, or land.
        this.getSpellAbility().addEffect(new MayTapOrUntapTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    public Twiddle(final Twiddle card) {
        super(card);
    }

    @Override
    public Twiddle copy() {
        return new Twiddle(this);
    }
}
