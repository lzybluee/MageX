
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class DesecrationPlague extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("enchantment or land");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.ENCHANTMENT), new CardTypePredicate(CardType.LAND)));
    }

    public DesecrationPlague(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}");


        // Destroy target enchantment or land.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

    }

    public DesecrationPlague(final DesecrationPlague card) {
        super(card);
    }

    @Override
    public DesecrationPlague copy() {
        return new DesecrationPlague(this);
    }
}
