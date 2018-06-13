
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author North
 */
public final class AnowonTheRuinSage extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("non-Vampire creature");

    static {
        filter.add(Predicates.not(new SubtypePredicate(SubType.VAMPIRE)));
    }

    public AnowonTheRuinSage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeAllEffect(filter), TargetController.YOU, false));
    }

    public AnowonTheRuinSage(final AnowonTheRuinSage card) {
        super(card);
    }

    @Override
    public AnowonTheRuinSage copy() {
        return new AnowonTheRuinSage(this);
    }
}
