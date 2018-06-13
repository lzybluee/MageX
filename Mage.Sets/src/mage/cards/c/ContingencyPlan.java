
package mage.cards.c;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;

/**
 *
 * @author LevelX2
 */
public final class ContingencyPlan extends CardImpl {

    public ContingencyPlan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{U}");

        // Look at the top five cards of your library. Put any number of them into your graveyard and the rest back on top of your library in any order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(new StaticValue(5), false, new StaticValue(5),
                new FilterCard("cards"), Zone.LIBRARY, true, false, true, Zone.GRAVEYARD, false));

    }

    public ContingencyPlan(final ContingencyPlan card) {
        super(card);
    }

    @Override
    public ContingencyPlan copy() {
        return new ContingencyPlan(this);
    }
}
