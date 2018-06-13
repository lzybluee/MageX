
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PhasingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class TeferisDrake extends CardImpl {

    public TeferisDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.DRAKE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Phasing
        this.addAbility(PhasingAbility.getInstance());
    }

    public TeferisDrake(final TeferisDrake card) {
        super(card);
    }

    @Override
    public TeferisDrake copy() {
        return new TeferisDrake(this);
    }
}
