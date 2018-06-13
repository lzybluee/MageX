
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.TargetPlayer;

/**
 *
 * @author jonubuu
 */
public final class Thoughtseize extends CardImpl {

    private static final FilterCard filter = new FilterCard("nonland card");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
    }

    public Thoughtseize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}");


        // Target player reveals their hand. You choose a nonland card from it. That player discards that card. You lose 2 life.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(filter, TargetController.ANY));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(2));
    }

    public Thoughtseize(final Thoughtseize card) {
        super(card);
    }

    @Override
    public Thoughtseize copy() {
        return new Thoughtseize(this);
    }
}

