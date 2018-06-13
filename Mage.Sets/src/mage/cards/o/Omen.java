
package mage.cards.o;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author fireshoes
 */
public final class Omen extends CardImpl {

    public Omen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{U}");

        // Look at the top three cards of your library, then put them back in any order. You may shuffle your library.
        this.getSpellAbility().addEffect(new LookLibraryControllerEffect(3, true));
        
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    public Omen(final Omen card) {
        super(card);
    }

    @Override
    public Omen copy() {
        return new Omen(this);
    }
}
