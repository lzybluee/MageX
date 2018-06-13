
package mage.cards.p;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class PrimalBellow extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("Forest you control");

    static {
        filter.add(new SubtypePredicate(SubType.FOREST));
        filter.add(new ControllerPredicate(TargetController.YOU));

    }

    public PrimalBellow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");

        PermanentsOnBattlefieldCount value = new PermanentsOnBattlefieldCount(filter);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(value, value, Duration.EndOfTurn));
    }

    public PrimalBellow(final PrimalBellow card) {
        super(card);
    }

    @Override
    public PrimalBellow copy() {
        return new PrimalBellow(this);
    }
}
