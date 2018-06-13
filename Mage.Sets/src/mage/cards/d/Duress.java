

package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetOpponent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class Duress extends CardImpl {

    private static final FilterCard filter = new FilterCard("a noncreature, nonland card");
    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
    }

    public Duress(UUID ownerId, CardSetInfo setInfo){
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}");


        // Target opponent reveals their hand. You choose a noncreature, nonland card from it. That player discards that card.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(filter));
    }

    public Duress(final Duress card) {
        super(card);
    }

    @Override
    public Duress copy() {
        return new Duress(this);
    }
}
