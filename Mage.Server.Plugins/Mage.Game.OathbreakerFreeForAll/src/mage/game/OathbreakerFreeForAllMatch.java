package mage.game;

import mage.game.match.MatchImpl;
import mage.game.match.MatchOptions;
import mage.game.mulligan.Mulligan;

/**
 * @author JayDi85
 */
public class OathbreakerFreeForAllMatch extends MatchImpl {

    public OathbreakerFreeForAllMatch(MatchOptions options) {
        super(options);
    }

    @Override
    public void startGame() throws GameException {
        int startLife = 20;
        Mulligan mulligan = options.getMulliganType().getMulligan(options.getFreeMulligans());
        OathbreakerFreeForAll game = new OathbreakerFreeForAll(options.getAttackOption(), options.getRange(), mulligan, startLife);
        game.setCheckCommanderDamage(false);
        game.setStartMessage(this.createGameStartMessage());
        initGame(game);
        games.add(game);
    }

}
