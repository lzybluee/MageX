
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author Plopman
 */
public final class Flashfires extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("Plains");
    
    static{
        filter.add(new SubtypePredicate(SubType.PLAINS));
    }
    
    public Flashfires(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}");


        // Destroy all Plains.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
        
    }

    public Flashfires(final Flashfires card) {
        super(card);
    }

    @Override
    public Flashfires copy() {
        return new Flashfires(this);
    }
}
