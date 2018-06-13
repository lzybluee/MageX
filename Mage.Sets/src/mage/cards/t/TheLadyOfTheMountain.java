
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author North
 */
public final class TheLadyOfTheMountain extends CardImpl {

    public TheLadyOfTheMountain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
    }

    public TheLadyOfTheMountain(final TheLadyOfTheMountain card) {
        super(card);
    }

    @Override
    public TheLadyOfTheMountain copy() {
        return new TheLadyOfTheMountain(this);
    }
}
