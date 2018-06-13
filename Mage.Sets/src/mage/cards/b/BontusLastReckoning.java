
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author spjspj
 */
public final class BontusLastReckoning extends CardImpl {

    public BontusLastReckoning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // Destroy all creatures. Lands you control don't untap during your next untap step.
        this.getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES));
        this.getSpellAbility().addEffect(new DontUntapInControllersUntapStepAllEffect(
                Duration.UntilYourNextTurn, TargetController.YOU, new FilterControlledLandPermanent("Lands you control"))
                .setText("Lands you control don't untap during your next untap step"));
    }

    public BontusLastReckoning(final BontusLastReckoning card) {
        super(card);
    }

    @Override
    public BontusLastReckoning copy() {
        return new BontusLastReckoning(this);
    }
}
