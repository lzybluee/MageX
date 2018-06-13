
package mage.cards.l;

import java.util.UUID;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public final class LayBareTheHeart extends CardImpl {

    private static final FilterCard filter = new FilterCard("nonlegendary, nonland card");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
        filter.add(Predicates.not(new SupertypePredicate(SuperType.LEGENDARY)));
    }

    public LayBareTheHeart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Target opponent reveals their hand. You choose a nonlegendary, nonland card from it. That player discards that card.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(filter, TargetController.ANY));
    }

    public LayBareTheHeart(final LayBareTheHeart card) {
        super(card);
    }

    @Override
    public LayBareTheHeart copy() {
        return new LayBareTheHeart(this);
    }
}
