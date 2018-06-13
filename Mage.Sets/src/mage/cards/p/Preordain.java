
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class Preordain extends CardImpl {

    public Preordain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}");

        // Scry 2, then draw a card. (To scry 2, look at the top two cards of your library, then put any number of them on the bottom of your library and the rest on top in any order.)
        this.getSpellAbility().addEffect(new ScryEffect(2));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    public Preordain(final Preordain card) {
        super(card);
    }

    @Override
    public Preordain copy() {
        return new Preordain(this);
    }
}
