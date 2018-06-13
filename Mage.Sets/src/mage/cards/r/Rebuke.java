
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterAttackingCreature;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author nantuko
 */
public final class Rebuke extends CardImpl {

    public Rebuke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");


        // Destroy target attacking creature.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(new FilterAttackingCreature()));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
    }

    public Rebuke(final Rebuke card) {
        super(card);
    }

    @Override
    public Rebuke copy() {
        return new Rebuke(this);
    }
}
