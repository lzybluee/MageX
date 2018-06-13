
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author fireshoes
 */
public final class TownSentry extends CardImpl {

    public TownSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Town Sentry blocks, it gets +0/+2 until end of turn.
        this.addAbility(new BlocksTriggeredAbility(new BoostSourceEffect(0, 2, Duration.EndOfTurn), false));
    }

    public TownSentry(final TownSentry card) {
        super(card);
    }

    @Override
    public TownSentry copy() {
        return new TownSentry(this);
    }
}
