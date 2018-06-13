

package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Backfir3
 */
public final class NightwindGlider extends CardImpl {

    public NightwindGlider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Protection from black
        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK));
    }

    public NightwindGlider(final NightwindGlider card) {
        super(card);
    }

    @Override
    public NightwindGlider copy() {
        return new NightwindGlider(this);
    }
}
