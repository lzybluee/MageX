
package mage.cards.c;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author TheElk801
 */
public final class CommuneWithDinosaurs extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Dinosaur or land card");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.LAND),
                new SubtypePredicate(SubType.DINOSAUR)
        ));
    }

    public CommuneWithDinosaurs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        //Look at the top five cards of your library. You may reveal a Dinosaur or land card from among them and put it into your hand. Put the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(new StaticValue(5), false, new StaticValue(1), filter, false));
    }

    public CommuneWithDinosaurs(final CommuneWithDinosaurs card) {
        super(card);
    }

    @Override
    public CommuneWithDinosaurs copy() {
        return new CommuneWithDinosaurs(this);
    }
}
