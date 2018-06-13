
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.UntapAllDuringEachOtherPlayersUntapStepEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CounterPredicate;

/**
 *
 * @author LevelX2
 */
public final class IvorytuskFortress extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("each creature you control with a +1/+1 counter on it");

    static {
        filter.add(new CounterPredicate(CounterType.P1P1));
    }

    public IvorytuskFortress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{B}{G}");
        this.subtype.add(SubType.ELEPHANT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // Untap each creature you control with a +1/+1 counter on it during each other player's untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new UntapAllDuringEachOtherPlayersUntapStepEffect(filter)));
    }

    public IvorytuskFortress(final IvorytuskFortress card) {
        super(card);
    }

    @Override
    public IvorytuskFortress copy() {
        return new IvorytuskFortress(this);
    }
}
