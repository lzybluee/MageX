
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTargetAmount;

/**
 *
 * @author North
 */
public final class FlamesOfTheFirebrand extends CardImpl {

    public FlamesOfTheFirebrand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}");


        // Flames of the Firebrand deals 3 damage divided as you choose among one, two, or three target creatures and/or players.
        this.getSpellAbility().addEffect(new DamageMultiEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTargetAmount(3));
    }

    public FlamesOfTheFirebrand(final FlamesOfTheFirebrand card) {
        super(card);
    }

    @Override
    public FlamesOfTheFirebrand copy() {
        return new FlamesOfTheFirebrand(this);
    }
}
