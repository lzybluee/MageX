package mage.cards.n;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class NecroticWound extends CardImpl {

    public NecroticWound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Undergrowth — Target creature gets -X/-X until end of turn, where X is the number of creature cards in your graveyard. If that creature would die this turn, exile it instead.
        DynamicValue xValue = new CardsInControllerGraveyardCount(
                StaticFilters.FILTER_CARD_CREATURE, -1
        );
        this.getSpellAbility().addEffect(
                new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn, true)
                        .setText("Target creature gets -X/-X until end of turn, "
                                + "where X is the number "
                                + "of creature cards in your graveyard.")
        );
        this.getSpellAbility().addEffect(new ExileTargetIfDiesEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().setAbilityWord(AbilityWord.UNDERGROWTH);
    }

    public NecroticWound(final NecroticWound card) {
        super(card);
    }

    @Override
    public NecroticWound copy() {
        return new NecroticWound(this);
    }
}
