
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInOpponentGraveCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class JacesPhantasm extends CardImpl {

    public JacesPhantasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.ILLUSION);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Jace's Phantasm gets +4/+4 as long as an opponent has ten or more cards in their graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(new BoostSourceEffect(4, 4, Duration.WhileOnBattlefield), new CardsInOpponentGraveCondition(10), "{this} gets +4/+4 as long as an opponent has ten or more cards in their graveyard")));
    }

    public JacesPhantasm(final JacesPhantasm card) {
        super(card);
    }

    @Override
    public JacesPhantasm copy() {
        return new JacesPhantasm(this);
    }
}
