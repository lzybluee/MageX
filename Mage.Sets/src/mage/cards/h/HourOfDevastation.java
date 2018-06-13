
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.continuous.LoseAbilityAllEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author ciaccona007
 */
public final class HourOfDevastation extends CardImpl {

    private static FilterPermanent filter = new FilterPermanent("creature and each non-Bolas planeswalker");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.CREATURE),
                Predicates.and(
                    new CardTypePredicate(CardType.PLANESWALKER),
                    Predicates.not(new SubtypePredicate(SubType.BOLAS)))));
    }
    
    public HourOfDevastation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");
        
        // All creatures lose indestructible until end of turn.
        Effect effect = new LoseAbilityAllEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("All creatures lose indestructible until end of turn");
        getSpellAbility().addEffect(effect);
        
        //Hour of Devastation deals 5 damage to each creature and each non-Bolas planeswalker.
        getSpellAbility().addEffect(new DamageAllEffect(5, filter));
        
    }

    public HourOfDevastation(final HourOfDevastation card) {
        super(card);
    }

    @Override
    public HourOfDevastation copy() {
        return new HourOfDevastation(this);
    }
}
