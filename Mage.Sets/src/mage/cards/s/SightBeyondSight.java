
package mage.cards.s;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;

/**
 *
 * @author LevelX2
 */
public final class SightBeyondSight extends CardImpl {

    public SightBeyondSight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}");

        // Look at the top two cards of your library. Put of them into your hand and the other on the bottom of your library.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(new StaticValue(2), false, new StaticValue(1), new FilterCard(), Zone.LIBRARY, false, false));
        
        // Rebound
        this.addAbility(new ReboundAbility());
    }

    public SightBeyondSight(final SightBeyondSight card) {
        super(card);
    }

    @Override
    public SightBeyondSight copy() {
        return new SightBeyondSight(this);
    }
}
