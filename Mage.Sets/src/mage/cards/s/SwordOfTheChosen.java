
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class SwordOfTheChosen extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("legendary creature");

    static {
        filter.add(new SupertypePredicate(SuperType.LEGENDARY));
    }

    public SwordOfTheChosen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        addSuperType(SuperType.LEGENDARY);

        // {tap}: Target legendary creature gets +2/+2 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(2, 2, Duration.EndOfTurn), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    public SwordOfTheChosen(final SwordOfTheChosen card) {
        super(card);
    }

    @Override
    public SwordOfTheChosen copy() {
        return new SwordOfTheChosen(this);
    }
}
