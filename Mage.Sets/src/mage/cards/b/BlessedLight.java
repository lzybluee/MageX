
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.ExileTargetEffect;
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
public final class BlessedLight extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or enchantment");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.CREATURE), new CardTypePredicate(CardType.ENCHANTMENT)));
    }

    public BlessedLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{W}");

        // Exile target creature or enchantment.
        getSpellAbility().addEffect(new ExileTargetEffect());
        getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    public BlessedLight(final BlessedLight card) {
        super(card);
    }

    @Override
    public BlessedLight copy() {
        return new BlessedLight(this);
    }
}
