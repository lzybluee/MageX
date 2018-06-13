
package mage.cards.l;

import java.util.UUID;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author North
 */
public final class LeadTheStampede extends CardImpl {

    private static final FilterCard filter = new FilterCard("any number of creature cards");
    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public LeadTheStampede(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}");

        // Look at the top five cards of your library. You may reveal any number of creature cards from among them and put the revealed cards into your hand. Put the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(5, 5, filter, true));
    }

    public LeadTheStampede(final LeadTheStampede card) {
        super(card);
    }

    @Override
    public LeadTheStampede copy() {
        return new LeadTheStampede(this);
    }
}
