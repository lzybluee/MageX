
package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ScryEffect extends OneShotEffect {

    protected static FilterCard filter1 = new FilterCard("card to put on the bottom of your library");

    protected int scryNumber;

    public ScryEffect(int scryNumber) {
        super(Outcome.Benefit);
        this.scryNumber = scryNumber;
        this.setText();
    }

    public ScryEffect(final ScryEffect effect) {
        super(effect);
        this.scryNumber = effect.scryNumber;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            return player.scry(scryNumber, source, game);
        }
        return false;
    }

    @Override
    public ScryEffect copy() {
        return new ScryEffect(this);
    }

    private void setText() {
        StringBuilder sb = new StringBuilder("scry ").append(scryNumber);
        if (scryNumber == 1) {
            sb.append(". <i>(Look at the top card of your library. You may put that card on the bottom of your library.)</i>");
        } else {
            sb.append(". <i>(Look at the top ");
            sb.append(CardUtil.numberToText(scryNumber));
            sb.append(" cards of your library, then put any number of them on the bottom of your library and the rest on top in any order.)</i>");
        }
        staticText = sb.toString();
    }
}
