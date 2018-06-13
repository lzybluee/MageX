
package mage.filter.common;

import java.util.UUID;
import mage.MageItem;
import mage.filter.FilterImpl;
import mage.filter.FilterInPlay;
import mage.filter.FilterPlayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FilterCreatureOrPlayer extends FilterImpl<MageItem> implements FilterInPlay<MageItem> {

    protected FilterCreaturePermanent creatureFilter;
    protected final FilterPlayer playerFilter;

    public FilterCreatureOrPlayer() {
        this("creature or player");
    }

    public FilterCreatureOrPlayer(String name) {
        super(name);
        creatureFilter = new FilterCreaturePermanent();
        playerFilter = new FilterPlayer();
    }

    public FilterCreatureOrPlayer(final FilterCreatureOrPlayer filter) {
        super(filter);
        this.creatureFilter = filter.creatureFilter.copy();
        this.playerFilter = filter.playerFilter.copy();
    }

    @Override
    public boolean checkObjectClass(Object object) {
        return true;
    }

    @Override
    public boolean match(MageItem o, Game game) {
        if (o instanceof Player) {
            return playerFilter.match((Player) o, game);
        } else if (o instanceof Permanent) {
            return creatureFilter.match((Permanent) o, game);
        }
        return false;
    }

    @Override
    public boolean match(MageItem o, UUID sourceId, UUID playerId, Game game) {
        if (o instanceof Player) {
            return playerFilter.match((Player) o, sourceId, playerId, game);
        } else if (o instanceof Permanent) {
            return creatureFilter.match((Permanent) o, sourceId, playerId, game);
        }
        return false;
    }

    public FilterCreaturePermanent getCreatureFilter() {
        return this.creatureFilter;
    }

    public FilterPlayer getPlayerFilter() {
        return this.playerFilter;
    }

    public void setCreatureFilter(FilterCreaturePermanent creatureFilter) {
        this.creatureFilter = creatureFilter;
    }

    @Override
    public FilterCreatureOrPlayer copy() {
        return new FilterCreatureOrPlayer(this);
    }

}
