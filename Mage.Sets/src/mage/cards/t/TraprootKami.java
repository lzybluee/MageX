
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetToughnessSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author Loki
 */
public final class TraprootKami extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("the number of Forests on the battlefield");

    static {
        filter.add(new SubtypePredicate(SubType.FOREST));
    }

    public TraprootKami(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
        // Defender; reach
        this.addAbility(DefenderAbility.getInstance());
        this.addAbility(ReachAbility.getInstance());
        // Traproot Kami's toughness is equal to the number of Forests on the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetToughnessSourceEffect(new PermanentsOnBattlefieldCount(filter), Duration.EndOfGame)));
    }

    public TraprootKami(final TraprootKami card) {
        super(card);
    }

    @Override
    public TraprootKami copy() {
        return new TraprootKami(this);
    }
}
