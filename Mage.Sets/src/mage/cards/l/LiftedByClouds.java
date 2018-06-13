
package mage.cards.l;

import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.SpliceOntoArcaneAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class LiftedByClouds extends CardImpl {

    public LiftedByClouds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");
        this.subtype.add(SubType.ARCANE);


        // Target creature gains flying until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // Splice onto Arcane {1}{U}
        this.addAbility(new SpliceOntoArcaneAbility("{1}{U}"));
    }

    public LiftedByClouds(final LiftedByClouds card) {
        super(card);
    }

    @Override
    public LiftedByClouds copy() {
        return new LiftedByClouds(this);
    }
}
