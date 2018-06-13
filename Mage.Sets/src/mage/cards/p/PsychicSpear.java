
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class PsychicSpear extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Spirit or Arcane card to discard");

    static {
        filter.add(Predicates.or(new SubtypePredicate(SubType.SPIRIT),new SubtypePredicate(SubType.ARCANE)));
    }

    public PsychicSpear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}");


        // Target player reveals their hand. You choose a Spirit or Arcane card from it. That player discards that card.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(filter, TargetController.ANY));
    }

    public PsychicSpear(final PsychicSpear card) {
        super(card);
    }

    @Override
    public PsychicSpear copy() {
        return new PsychicSpear(this);
    }
}
