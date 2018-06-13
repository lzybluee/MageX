

package mage.cards.s;

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
 * @author Loki
 */
public final class Soulscour extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("nonartifact permanents");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.ARTIFACT)));
    }

    public Soulscour (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{7}{W}{W}{W}");


        // Destroy all nonartifact permanents.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    public Soulscour (final Soulscour card) {
        super(card);
    }

    @Override
    public Soulscour copy() {
        return new Soulscour(this);
    }

}
