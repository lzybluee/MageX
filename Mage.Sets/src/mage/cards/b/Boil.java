
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author Loki
 */
public final class Boil extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Islands");

    static {
        filter.add(new SubtypePredicate(SubType.ISLAND));
    }

    public Boil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{R}");

        
        // Destroy all Islands.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    public Boil(final Boil card) {
        super(card);
    }

    @Override
    public Boil copy() {
        return new Boil(this);
    }
}
