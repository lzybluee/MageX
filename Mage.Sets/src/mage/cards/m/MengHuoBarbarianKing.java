
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author fireshoes
 */
public final class MengHuoBarbarianKing extends CardImpl {
    
    private static final FilterCreaturePermanent filterGreen = new FilterCreaturePermanent("green creatures you control");

    static {
        filterGreen.add(new ColorPredicate(ObjectColor.GREEN));
        filterGreen.add(new ControllerPredicate(TargetController.YOU));
    }

    public MengHuoBarbarianKing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BARBARIAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Other green creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filterGreen, true)));
    }

    public MengHuoBarbarianKing(final MengHuoBarbarianKing card) {
        super(card);
    }

    @Override
    public MengHuoBarbarianKing copy() {
        return new MengHuoBarbarianKing(this);
    }
}
