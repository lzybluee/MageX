
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author North
 */
public final class MindSculpt extends CardImpl {

    public MindSculpt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{U}");


        // Target opponent puts the top seven cards of their library into their graveyard.
        this.getSpellAbility().addEffect(new PutLibraryIntoGraveTargetEffect(7));
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public MindSculpt(final MindSculpt card) {
        super(card);
    }

    @Override
    public MindSculpt copy() {
        return new MindSculpt(this);
    }
}
