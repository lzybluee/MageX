
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class MonstrousGrowth extends CardImpl {

    public MonstrousGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}");


        // Target creature gets +4/+4 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(4, 4, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public MonstrousGrowth(final MonstrousGrowth card) {
        super(card);
    }

    @Override
    public MonstrousGrowth copy() {
        return new MonstrousGrowth(this);
    }
}
