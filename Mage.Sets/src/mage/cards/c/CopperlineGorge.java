
package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.StaticFilters;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public final class CopperlineGorge extends CardImpl {

    public CopperlineGorge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);

        // Copperline Gorge enters the battlefield tapped unless you control two or fewer other lands.
        Condition controls = new InvertCondition(new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_LANDS, ComparisonType.FEWER_THAN, 3));
        String abilityText = " tapped unless you control two or fewer other lands";
        this.addAbility(new EntersBattlefieldAbility(new ConditionalOneShotEffect(new TapSourceEffect(), controls, abilityText), abilityText));
        this.addAbility(new RedManaAbility());
        this.addAbility(new GreenManaAbility());
    }

    public CopperlineGorge(final CopperlineGorge card) {
        super(card);
    }

    @Override
    public CopperlineGorge copy() {
        return new CopperlineGorge(this);
    }

}
