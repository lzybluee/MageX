
package mage.cards.s;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public final class Solemnity extends CardImpl {

    public Solemnity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Players can't get counters.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SolemnityEffect()));

        // Counters can't be put on artifacts, creatures, enchantments, or lands.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SolemnityEffect2()));
    }

    public Solemnity(final Solemnity card) {
        super(card);
    }

    @Override
    public Solemnity copy() {
        return new Solemnity(this);
    }
}

class SolemnityEffect extends ReplacementEffectImpl {

    public SolemnityEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Players can't get counters";
    }

    public SolemnityEffect(final SolemnityEffect effect) {
        super(effect);
    }

    @Override
    public SolemnityEffect copy() {
        return new SolemnityEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ADD_COUNTER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getTargetId());
        return player != null;
    }
}

class SolemnityEffect2 extends ReplacementEffectImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.ENCHANTMENT),
                new CardTypePredicate(CardType.LAND)));
    }

    public SolemnityEffect2() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Counters can't be put on artifacts, creatures, enchantments, or lands";
    }

    public SolemnityEffect2(final SolemnityEffect2 effect) {
        super(effect);
    }

    @Override
    public SolemnityEffect2 copy() {
        return new SolemnityEffect2(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ADD_COUNTER || event.getType() == EventType.ADD_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        MageObject object = game.getObject(event.getTargetId());
        Permanent permanent = game.getPermanentEntering(event.getSourceId());
        Permanent permanent2 = game.getPermanent(event.getTargetId());
        Permanent permanent3 = game.getPermanentEntering(event.getTargetId());

        if (object != null && object instanceof Permanent) {
            if (filter.match((Permanent) object, game)) {
                return true;
            }
        } else if (permanent != null) {
            if (filter.match(permanent, game)) {
                return true;
            }
        } else if (permanent2 != null) {
            if (filter.match(permanent2, game)) {
                return true;
            }
        } else if (permanent3 != null) {
            if (filter.match(permanent3, game)) {
                return true;
            }
        }

        return false;
    }
}
