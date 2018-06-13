
package mage.cards.s;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class SpittingEarth extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Mountain you control");

    static {
        filter.add(new SubtypePredicate(SubType.MOUNTAIN));
    }

    public SpittingEarth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");

        this.getSpellAbility().addEffect(new DamageTargetEffect(new PermanentsOnBattlefieldCount(filter)));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public SpittingEarth(final SpittingEarth card) {
        super(card);
    }

    @Override
    public SpittingEarth copy() {
        return new SpittingEarth(this);
    }
}
