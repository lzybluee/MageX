
package mage.cards.v;

import mage.ObjectColor;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.abilities.keyword.SpliceOntoArcaneAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class VeilOfSecrecy extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("a blue creature");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public VeilOfSecrecy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");
        this.subtype.add(SubType.ARCANE);

        // Target creature gains shroud until end of turn and is can't be blocked this turn.
        Effect effect = new GainAbilityTargetEffect(ShroudAbility.getInstance(), Duration.EndOfTurn);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        effect.setText("Target creature gains shroud until end of turn");
        this.getSpellAbility().addEffect(effect);
        effect = new CantBeBlockedTargetEffect();
        effect.setText("and can't be blocked this turn");
        this.getSpellAbility().addEffect(effect);
        
        // Splice onto Arcane-Return a blue creature you control to its owner's hand.
        this.addAbility(new SpliceOntoArcaneAbility(new ReturnToHandChosenControlledPermanentCost(new TargetControlledCreaturePermanent(filter))));
    }

    public VeilOfSecrecy(final VeilOfSecrecy card) {
        super(card);
    }

    @Override
    public VeilOfSecrecy copy() {
        return new VeilOfSecrecy(this);
    }
}
