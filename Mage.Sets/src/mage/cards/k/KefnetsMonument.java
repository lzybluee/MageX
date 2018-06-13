
package mage.cards.k;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class KefnetsMonument extends CardImpl {

    private static final FilterCard filter = new FilterCard("Blue creature spells");
    private static final FilterSpell filter2 = new FilterSpell("a creature spell");
    private static final FilterCreaturePermanent filter3 = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(Predicates.and(new ColorPredicate(ObjectColor.BLUE), new CardTypePredicate(CardType.CREATURE)));
    }
    static {
        filter2.add(new CardTypePredicate(CardType.CREATURE));
    }
    static {
        filter3.add(new ControllerPredicate(TargetController.OPPONENT));
    }


    public KefnetsMonument(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        addSuperType(SuperType.LEGENDARY);

        // Blue creature spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 1)));

        // Whenever you cast a creature spell, target creature an opponent controls doesn't untap during its controller's next untap step.
        Ability ability = new SpellCastControllerTriggeredAbility(new DontUntapInControllersNextUntapStepTargetEffect(), filter2, false);
        ability.addTarget(new TargetCreaturePermanent(filter3));
        this.addAbility(ability);
    }

    public KefnetsMonument(final KefnetsMonument card) {
        super(card);
    }

    @Override
    public KefnetsMonument copy() {
        return new KefnetsMonument(this);
    }
}
