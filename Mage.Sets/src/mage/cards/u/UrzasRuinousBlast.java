package mage.cards.u;

import java.util.UUID;

import mage.abilities.common.LegendarySpellAbility;
import mage.abilities.effects.common.ExileAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;

/**
 * @author JRHerlehy
 *         Created on 4/8/18.
 */
public final class UrzasRuinousBlast extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("nonland permanents that aren't legendary");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
        filter.add(Predicates.not(new SupertypePredicate(SuperType.LEGENDARY)));
    }

    public UrzasRuinousBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}");
        this.addSuperType(SuperType.LEGENDARY);

        // (You may cast a legendary sorcery only if you control a legendary creature or planeswalker.)
        this.addAbility(new LegendarySpellAbility());

        // Exile all nonland permanents that aren't legendary.
        this.getSpellAbility().addEffect(new ExileAllEffect(filter));
    }

    public UrzasRuinousBlast(final UrzasRuinousBlast card) {
        super(card);
    }

    @Override
    public UrzasRuinousBlast copy() {
        return new UrzasRuinousBlast(this);
    }
}
