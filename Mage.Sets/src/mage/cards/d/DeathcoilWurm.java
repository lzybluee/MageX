
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DamageAsThoughNotBlockedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author nigelzor
 */
public final class DeathcoilWurm extends CardImpl {

    public DeathcoilWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{G}{G}");
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // You may have Deathcoil Wurm assign its combat damage as though it weren't blocked.
        this.addAbility(DamageAsThoughNotBlockedAbility.getInstance());
    }

    public DeathcoilWurm(final DeathcoilWurm card) {
        super(card);
    }

    @Override
    public DeathcoilWurm copy() {
        return new DeathcoilWurm(this);
    }
}
