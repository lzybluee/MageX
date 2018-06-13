
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author Loki
 */
public final class Aftershock extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact, creature, or land");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.LAND)));
    }

    public Aftershock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}{R}");

        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new DamageControllerEffect(3));
    }

    public Aftershock(final Aftershock card) {
        super(card);
    }

    @Override
    public Aftershock copy() {
        return new Aftershock(this);
    }
}
