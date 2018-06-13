
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterAttackingCreature;

/**
 *
 * @author North
 */
public final class TrumpetBlast extends CardImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("Attacking creatures");

    public TrumpetBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");

        // Attacking creatures get +2/+0 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(2, 0, Duration.EndOfTurn, filter, false));
    }

    public TrumpetBlast(final TrumpetBlast card) {
        super(card);
    }

    @Override
    public TrumpetBlast copy() {
        return new TrumpetBlast(this);
    }
}
