
package mage.cards.l;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetWithReplacementEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.constants.ZoneDetail;
import mage.target.TargetSpell;

/**
 *
 * @author North
 */
public final class LapseOfCertainty extends CardImpl {

    public LapseOfCertainty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");

        // Counter target spell. If that spell is countered this way, put it on top of its owner's library instead of into that player's graveyard.
        this.getSpellAbility().addEffect(new CounterTargetWithReplacementEffect(Zone.LIBRARY, ZoneDetail.TOP));
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    public LapseOfCertainty(final LapseOfCertainty card) {
        super(card);
    }

    @Override
    public LapseOfCertainty copy() {
        return new LapseOfCertainty(this);
    }
}
