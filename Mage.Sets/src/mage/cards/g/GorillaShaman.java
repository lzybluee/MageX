
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetAdjustment;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class GorillaShaman extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("noncreature artifact with converted mana cost X");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
        filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    public GorillaShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.APE);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {X}{X}{1}: Destroy target noncreature artifact with converted mana cost X.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl("{X}{X}{1}"));
        ability.addTarget(new TargetPermanent(filter));
        ability.setTargetAdjustment(TargetAdjustment.X_CMC_EQUAL_PERM);
        this.addAbility(ability);
    }

    public GorillaShaman(final GorillaShaman card) {
        super(card);
    }

    @Override
    public GorillaShaman copy() {
        return new GorillaShaman(this);
    }
}
