
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.combat.BlocksIfAbleAllEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.BlocksThisTurnMarkerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 * @author magenoxx_at_gmail.com
 */
public final class PredatoryRampage extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Each creature your opponents control");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public PredatoryRampage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}{G}");


        // Creatures you control get +3/+3 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(3, 3, Duration.EndOfTurn));
        // Each creature your opponents control blocks this turn if able.
        this.getSpellAbility().addEffect(new BlocksIfAbleAllEffect(filter, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new GainAbilityAllEffect(BlocksThisTurnMarkerAbility.getInstance(), Duration.EndOfTurn, filter, ""));
    }

    public PredatoryRampage(final PredatoryRampage card) {
        super(card);
    }

    @Override
    public PredatoryRampage copy() {
        return new PredatoryRampage(this);
    }
}
