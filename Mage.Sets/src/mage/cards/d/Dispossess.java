
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public final class Dispossess extends CardImpl {

    public Dispossess(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Name an artifact card. Search target player's graveyard, hand, and library for any number of cards with that name and exile them. Then that player shuffles their library.
        this.getSpellAbility().addEffect((new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.ARTIFACT_NAME)));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new DispossessEffect());
    }

    public Dispossess(final Dispossess card) {
        super(card);
    }

    @Override
    public Dispossess copy() {
        return new Dispossess(this);
    }
}

class DispossessEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {

    public DispossessEffect() {
        super(true, "target opponent's", "any number of cards with that name");
    }

    public DispossessEffect(final DispossessEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        return super.applySearchAndExile(game, source, cardName, targetPointer.getFirst(game, source));
    }

    @Override
    public DispossessEffect copy() {
        return new DispossessEffect(this);
    }

}
