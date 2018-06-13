
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SpellAbilityType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

public final class ProfitLoss extends SplitCard {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures your opponents control");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public ProfitLoss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}", "{2}{B}", SpellAbilityType.SPLIT_FUSED);

        // Profit
        // Creatures you control get +1/+1 until end of turn.
        getLeftHalfCard().getSpellAbility().addEffect(new BoostControlledEffect(1, 1, Duration.EndOfTurn, new FilterCreaturePermanent()));

        // Loss
        // Creatures your opponents control get -1/-1 until end of turn.
        getRightHalfCard().getSpellAbility().addEffect(new BoostAllEffect(-1, -1, Duration.EndOfTurn, filter, false));

    }

    public ProfitLoss(final ProfitLoss card) {
        super(card);
    }

    @Override
    public ProfitLoss copy() {
        return new ProfitLoss(this);
    }
}
