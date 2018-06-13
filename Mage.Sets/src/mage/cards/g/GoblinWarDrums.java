
package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author LevelX2
 */
 public final class GoblinWarDrums extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures you control");
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public GoblinWarDrums(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");


        // Creatures you control have menace. (They can't be blocked except by two or more creatures.)
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(new MenaceAbility(), Duration.WhileOnBattlefield, filter)));
    }

    public GoblinWarDrums(final GoblinWarDrums card) {
        super(card);
    }

    @Override
    public GoblinWarDrums copy() {
        return new GoblinWarDrums(this);
    }
}
