
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author Loki
 */
public final class TerashisVerdict extends CardImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("attacking creature with power 3 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 4));
    }

    public TerashisVerdict(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");
        this.subtype.add(SubType.ARCANE);

        // Destroy target attacking creature with power 3 or less.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetAttackingCreature(1, 1, filter, false));
    }

    public TerashisVerdict(final TerashisVerdict card) {
        super(card);
    }

    @Override
    public TerashisVerdict copy() {
        return new TerashisVerdict(this);
    }
}
