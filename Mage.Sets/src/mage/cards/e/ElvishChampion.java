
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.ForestwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author Loki
 */
public final class ElvishChampion extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Elf creatures");
    static {
        filter.add(new SubtypePredicate(SubType.ELF));
    }

    public ElvishChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.ELF);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other Elf creatures get +1/+1 and have forestwalk. (They can't be blocked as long as defending player controls a Forest.)
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(new ForestwalkAbility(), Duration.WhileOnBattlefield, filter, true)));
    }

    public ElvishChampion(final ElvishChampion card) {
        super(card);
    }

    @Override
    public ElvishChampion copy() {
        return new ElvishChampion(this);
    }
}
