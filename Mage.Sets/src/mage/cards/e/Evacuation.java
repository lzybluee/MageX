
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURES;

/**
 *
 * @author Plopman
 */
public final class Evacuation extends CardImpl {

    public Evacuation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{U}");

        // Return all creatures to their owners' hands.
        this.getSpellAbility().addEffect(new ReturnToHandFromBattlefieldAllEffect(FILTER_PERMANENT_CREATURES));
    }

    public Evacuation(final Evacuation card) {
        super(card);
    }

    @Override
    public Evacuation copy() {
        return new Evacuation(this);
    }
}
