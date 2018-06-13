
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.RetraceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.KithkinToken;

/**
 *
 * @author Plopman
 */
public final class CennsEnlistment extends CardImpl {

    public CennsEnlistment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{W}");

        // Create two 1/1 white Kithkin Soldier creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new KithkinToken(), 2));

        // Retrace
        this.addAbility(new RetraceAbility(this));
    }

    public CennsEnlistment(final CennsEnlistment card) {
        super(card);
    }

    @Override
    public CennsEnlistment copy() {
        return new CennsEnlistment(this);
    }
}
