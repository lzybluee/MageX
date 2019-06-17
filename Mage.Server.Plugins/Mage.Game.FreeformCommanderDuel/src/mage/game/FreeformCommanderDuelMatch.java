package mage.game;

import mage.game.match.MatchImpl;
import mage.game.match.MatchOptions;
import mage.game.mulligan.Mulligan;

/**
 * @author JayDi85
 */
public class FreeformCommanderDuelMatch extends MatchImpl {

    public FreeformCommanderDuelMatch(MatchOptions options) {
        super(options);
    }

    @Override
    public void startGame() throws GameException {
        int startLife = 20;
        boolean alsoHand = true;
        boolean checkCommanderDamage = true;

        Mulligan mulligan = options.getMulliganType().getMulligan(options.getFreeMulligans());
        FreeformCommanderDuel game = new FreeformCommanderDuel(options.getAttackOption(), options.getRange(), mulligan, startLife);
        game.setCheckCommanderDamage(checkCommanderDamage);
        game.setStartMessage(this.createGameStartMessage());
        game.setAlsoHand(alsoHand);
        game.setAlsoLibrary(true);
        initGame(game);
        games.add(game);
    }
}
