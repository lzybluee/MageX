
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author KholdFuzion
 */
public final class KingSuleiman extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Djinn or Efreet");

    static {
        filter.add( Predicates.or(
                new SubtypePredicate(SubType.DJINN),
                new SubtypePredicate(SubType.EFREET)));
    }

    public KingSuleiman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Destroy target Djinn or Efreet.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    public KingSuleiman(final KingSuleiman card) {
        super(card);
    }

    @Override
    public KingSuleiman copy() {
        return new KingSuleiman(this);
    }
}
