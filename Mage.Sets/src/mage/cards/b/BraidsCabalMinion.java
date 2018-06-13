
package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

import java.util.UUID;

/**
 *
 * @author cbt33, North (Karma)
 */
public final class BraidsCabalMinion extends CardImpl {

    static final FilterPermanent filter = new FilterPermanent("an artifact, creature, or land");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.LAND)));
    }

    public BraidsCabalMinion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.MINION);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of each player's upkeep, that player sacrifices an artifact, creature, or land.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new SacrificeEffect(filter, 1, "that player"), TargetController.ANY, false));

    }

    public BraidsCabalMinion(final BraidsCabalMinion card) {
        super(card);
    }

    @Override
    public BraidsCabalMinion copy() {
        return new BraidsCabalMinion(this);
    }
}
