
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class GrizzlyBears extends CardImpl {

    public GrizzlyBears(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.BEAR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
    }

    public GrizzlyBears(final GrizzlyBears card) {
        super(card);
    }

    @Override
    public GrizzlyBears copy() {
        return new GrizzlyBears(this);
    }
}
