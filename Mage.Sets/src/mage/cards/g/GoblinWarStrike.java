
package mage.cards.g;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author LevelX2
 */
public final class GoblinWarStrike extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Goblins you control");

    static {
        filter.add(new SubtypePredicate(SubType.GOBLIN));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public GoblinWarStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Goblin War Strike deals damage equal to the number of Goblins you control to target player.
        this.getSpellAbility().addEffect(new DamageTargetEffect(new PermanentsOnBattlefieldCount(filter)));
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
    }

    public GoblinWarStrike(final GoblinWarStrike card) {
        super(card);
    }

    @Override
    public GoblinWarStrike copy() {
        return new GoblinWarStrike(this);
    }
}
