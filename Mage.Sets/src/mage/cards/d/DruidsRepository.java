
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author noxx

 */
public final class DruidsRepository extends CardImpl {

    public DruidsRepository(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{G}{G}");


        // Whenever a creature you control attacks, put a charge counter on Druids' Repository.
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(new AddCountersSourceEffect(CounterType.CHARGE.createInstance())));

        // Remove a charge counter from Druids' Repository: Add one mana of any color.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(), new RemoveCountersSourceCost(CounterType.CHARGE.createInstance()));        
        this.addAbility(ability);
    }

    public DruidsRepository(final DruidsRepository card) {
        super(card);
    }

    @Override
    public DruidsRepository copy() {
        return new DruidsRepository(this);
    }
}
