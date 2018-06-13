
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

/**
 *
 * @author Jesse Whyte
 */
public final class RetributionOfTheMeek extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 4 or greater");
    
    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }
    
    public RetributionOfTheMeek (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{W}");
        
        // Destroy all creatures with power 4 or greater. They can't be regenerated.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter, true));
    }
    
    public RetributionOfTheMeek(final RetributionOfTheMeek card) {
        super(card);
    }
    
    @Override
    public Card copy() {
        return new RetributionOfTheMeek(this);
    }
    
}
