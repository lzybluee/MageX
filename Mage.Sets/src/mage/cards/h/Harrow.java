

package mage.cards.h;

import java.util.UUID;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterBasicLandCard;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Viserion
 */
public final class Harrow extends CardImpl {

    public Harrow(UUID ownerId, CardSetInfo setInfo){
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}");
        this.color.setGreen(true);        

        // As an additional cost to cast Harrow, sacrifice a land.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledLandPermanent("land"))));

        // Search your library for up to two basic land cards and put them onto the battlefield. Then shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary(0, 2, StaticFilters.FILTER_CARD_BASIC_LAND);
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(target, false, Outcome.PutLandInPlay));
    }

    public Harrow(final Harrow card) {
        super(card);
    }

    @Override
    public Harrow copy() {
        return new Harrow(this);
    }
}
