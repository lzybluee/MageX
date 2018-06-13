
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class NeedletoothRaptor extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public NeedletoothRaptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // <i>Enrage</i> &mdash; Whenever Needletooth Raptor is dealt damage, it deals 5 damage to target creature an opponent controls.
        Ability ability = new DealtDamageToSourceTriggeredAbility(Zone.BATTLEFIELD, new DamageTargetEffect(5).setText("it deals 5 damage to target creature an opponent controls"), false, true);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    public NeedletoothRaptor(final NeedletoothRaptor card) {
        super(card);
    }

    @Override
    public NeedletoothRaptor copy() {
        return new NeedletoothRaptor(this);
    }
}
