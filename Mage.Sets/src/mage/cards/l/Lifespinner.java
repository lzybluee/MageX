
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class Lifespinner extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard("legendary Spirit permanent card");

    static {
        filter.add(new SupertypePredicate(SuperType.LEGENDARY));
        filter.add(new SubtypePredicate(SubType.SPIRIT));
    }

    public Lifespinner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {t}, Sacrifice three Spirits: Search your library for a legendary Spirit permanent card and put it onto the battlefield. Then shuffle your library.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter)),
                new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(3, 3, new FilterControlledCreaturePermanent(SubType.SPIRIT, "three Spirits"), false)));
        this.addAbility(ability);
    }

    public Lifespinner(final Lifespinner card) {
        super(card);
    }

    @Override
    public Lifespinner copy() {
        return new Lifespinner(this);
    }
}
