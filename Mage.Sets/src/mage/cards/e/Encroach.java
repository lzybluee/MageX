
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LoneFox
 */
public final class Encroach extends CardImpl {

    private static final FilterCard filter = new FilterCard("a nonbasic land card");

    static {
        filter.add(Predicates.not(new SupertypePredicate(SuperType.BASIC)));
        filter.add(new CardTypePredicate(CardType.LAND));
    }

    public Encroach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}");

        // Target player reveals their hand. You choose a nonbasic land card from it. That player discards that card.
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(filter));
        this.getSpellAbility().addTarget(new TargetOpponent());
   }

    public Encroach(final Encroach card) {
        super(card);
    }

    @Override
    public Encroach copy() {
        return new Encroach(this);
    }
}
