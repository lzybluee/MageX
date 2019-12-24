package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.FilterPlayer;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.filter.predicate.other.PlayerIdPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class PreventAllDamageToAllEffect extends PreventionEffectImpl {

    protected FilterPermanentOrPlayer filter;

    public PreventAllDamageToAllEffect(Duration duration, FilterPermanent filterPermanent) {
        this(duration, createFilter(filterPermanent, null));
    }

    public PreventAllDamageToAllEffect(Duration duration, FilterPermanent filterPermanent, boolean onlyCombat) {
        this(duration, createFilter(filterPermanent, null), onlyCombat);
    }

    public PreventAllDamageToAllEffect(Duration duration, FilterPermanentOrPlayer filter) {
        this(duration, filter, false);
    }

    public PreventAllDamageToAllEffect(Duration duration, FilterPermanentOrPlayer filter, boolean onlyCombat) {
        super(duration, Integer.MAX_VALUE, onlyCombat);
        this.filter = filter;
        staticText = "Prevent all "
                + (onlyCombat ? "combat " : "")
                + "damage that would be dealt to "
                + filter.getMessage()
                + (duration.toString().isEmpty() ? "" : ' ' + duration.toString());
    }

    public PreventAllDamageToAllEffect(final PreventAllDamageToAllEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    private static FilterPermanentOrPlayer createFilter(FilterPermanent filterPermanent, FilterPlayer filterPlayer) {
        String message = String.join(
                " and ",
                filterPermanent != null ? filterPermanent.getMessage() : "",
                filterPlayer != null ? filterPlayer.getMessage() : "");
        FilterPermanent filter1 = filterPermanent;
        if (filter1 == null) {
            filter1 = new FilterPermanent();
            filter1.add(new PermanentIdPredicate(UUID.randomUUID())); // disable filter
        }
        FilterPlayer filter2 = filterPlayer;
        if (filter2 == null) {
            filter2 = new FilterPlayer();
            filter2.add(new PlayerIdPredicate(UUID.randomUUID())); // disable filter
        }

        return new FilterPermanentOrPlayer(message, filter1, filter2);
    }

    @Override
    public PreventAllDamageToAllEffect copy() {
        return new PreventAllDamageToAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            MageObject object = game.getObject(event.getTargetId());
            if (object != null) {
                return filter.match(object, source.getSourceId(), source.getControllerId(), game);
            }
        }
        return false;
    }

}
