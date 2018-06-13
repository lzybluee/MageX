
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Quercitron
 */
public final class MentalNote extends CardImpl {

    public MentalNote(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");


        // Put the top two cards of your library into your graveyard.
        this.getSpellAbility().addEffect(new PutTopCardOfLibraryIntoGraveControllerEffect(2));
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    public MentalNote(final MentalNote card) {
        super(card);
    }

    @Override
    public MentalNote copy() {
        return new MentalNote(this);
    }
}
