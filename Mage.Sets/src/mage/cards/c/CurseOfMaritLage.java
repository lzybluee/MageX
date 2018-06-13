
package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.abilities.effects.common.TapAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author fireshoes
 */
public final class CurseOfMaritLage extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("Islands");
    
    static {
        filter.add(new SubtypePredicate(SubType.ISLAND));
    }    

    public CurseOfMaritLage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{R}{R}");

        // When Curse of Marit Lage enters the battlefield, tap all Islands.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TapAllEffect(filter)));
        
        // Islands don't untap during their controllers' untap steps.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, 
                new DontUntapInControllersUntapStepAllEffect(Duration.WhileOnBattlefield, TargetController.ANY, filter)));
    }

    public CurseOfMaritLage(final CurseOfMaritLage card) {
        super(card);
    }

    @Override
    public CurseOfMaritLage copy() {
        return new CurseOfMaritLage(this);
    }
}
