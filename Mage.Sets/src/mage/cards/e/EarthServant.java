

package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author BetaSteward_at_googlemail.com, North
 */
public final class EarthServant extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("Mountain you control");

    static {
        filter.add(new SubtypePredicate(SubType.MOUNTAIN));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public EarthServant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(new PermanentsOnBattlefieldCount(filter, 0),
                new PermanentsOnBattlefieldCount(filter, 1),
                Duration.WhileOnBattlefield)));
    }

    public EarthServant(final EarthServant card) {
        super(card);
    }

    @Override
    public EarthServant copy() {
        return new EarthServant(this);
    }

}
