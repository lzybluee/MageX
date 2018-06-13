
package mage.cards.h;

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
public final class Hinder extends CardImpl {

    public Hinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}{U}");

        // Counter target spell. If that spell is countered this way, put that card on the top or bottom of its owner's library instead of into that player's graveyard.
        this.getSpellAbility().addEffect(new CounterTargetWithReplacementEffect(Zone.LIBRARY, ZoneDetail.CHOOSE));
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    public Hinder(final Hinder card) {
        super(card);
    }

    @Override
    public Hinder copy() {
        return new Hinder(this);
    }
}
