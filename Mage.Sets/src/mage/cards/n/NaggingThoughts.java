
package mage.cards.n;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;

/**
 *
 * @author LevelX2
 */
public final class NaggingThoughts extends CardImpl {

    public NaggingThoughts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{U}");

        // Look at the top two cards of your library. Put one of them into your hand and the other into your graveyard.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(new StaticValue(2), false, new StaticValue(1), new FilterCard(), Zone.GRAVEYARD, false, false));

        // Madness {1}{U}
        this.addAbility(new MadnessAbility(this, new ManaCostsImpl("{1}{U}")));
    }

    public NaggingThoughts(final NaggingThoughts card) {
        super(card);
    }

    @Override
    public NaggingThoughts copy() {
        return new NaggingThoughts(this);
    }
}
