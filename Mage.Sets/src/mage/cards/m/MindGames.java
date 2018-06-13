
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class MindGames extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("artifact, creature, or land");
    
    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.LAND)));
    }

    public MindGames(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Buyback {2}{U}
        this.addAbility(new BuybackAbility("{2}{U}"));
        
        // Tap target artifact, creature, or land.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    public MindGames(final MindGames card) {
        super(card);
    }

    @Override
    public MindGames copy() {
        return new MindGames(this);
    }
}
