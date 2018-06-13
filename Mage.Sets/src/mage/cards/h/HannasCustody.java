
package mage.cards.h;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author Loki
 */
public final class HannasCustody extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifacts");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public HannasCustody(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}");

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(ShroudAbility.getInstance(), Duration.WhileOnBattlefield, filter, false)));
    }

    public HannasCustody(final HannasCustody card) {
        super(card);
    }

    @Override
    public HannasCustody copy() {
        return new HannasCustody(this);
    }
}
