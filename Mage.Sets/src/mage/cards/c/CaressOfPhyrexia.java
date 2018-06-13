
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.counter.AddPoisonCounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public final class CaressOfPhyrexia extends CardImpl {

    public CaressOfPhyrexia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}{B}");


        this.getSpellAbility().addEffect(new DrawCardTargetEffect(3));
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(3));
        this.getSpellAbility().addEffect(new AddPoisonCounterTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public CaressOfPhyrexia(final CaressOfPhyrexia card) {
        super(card);
    }

    @Override
    public CaressOfPhyrexia copy() {
        return new CaressOfPhyrexia(this);
    }
}
