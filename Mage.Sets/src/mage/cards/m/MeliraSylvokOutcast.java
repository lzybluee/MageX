
package mage.cards.m;

import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward
 */
public final class MeliraSylvokOutcast extends CardImpl {

    public MeliraSylvokOutcast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // You can't get poison counters.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MeliraSylvokOutcastEffect()));

        // Creatures you control can't have -1/-1 counters put on them.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MeliraSylvokOutcastEffect2()));

        // Creatures your opponents control lose infect.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MeliraSylvokOutcastEffect3()));

    }

    public MeliraSylvokOutcast(final MeliraSylvokOutcast card) {
        super(card);
    }

    @Override
    public MeliraSylvokOutcast copy() {
        return new MeliraSylvokOutcast(this);
    }
}

class MeliraSylvokOutcastEffect extends ReplacementEffectImpl {

    public MeliraSylvokOutcastEffect() {
        super(Duration.WhileOnBattlefield, Outcome.PreventDamage);
        staticText = "You can't get poison counters";
    }

    public MeliraSylvokOutcastEffect(final MeliraSylvokOutcastEffect effect) {
        super(effect);
    }

    @Override
    public MeliraSylvokOutcastEffect copy() {
        return new MeliraSylvokOutcastEffect(this);
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
        return event.getData().equals(CounterType.POISON.getName()) && event.getTargetId().equals(source.getControllerId());
    }

}

class MeliraSylvokOutcastEffect2 extends ReplacementEffectImpl {

    public MeliraSylvokOutcastEffect2() {
        super(Duration.WhileOnBattlefield, Outcome.PreventDamage);
        staticText = "Creatures you control can't have -1/-1 counters put on them";
    }

    public MeliraSylvokOutcastEffect2(final MeliraSylvokOutcastEffect2 effect) {
        super(effect);
    }

    @Override
    public MeliraSylvokOutcastEffect2 copy() {
        return new MeliraSylvokOutcastEffect2(this);
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
        if (event.getData().equals(CounterType.M1M1.getName())) {
            Permanent perm = game.getPermanent(event.getTargetId());
            if (perm == null) {
                perm = game.getPermanentEntering(event.getTargetId());
            }
            if (perm != null && perm.isCreature() && perm.getControllerId().equals(source.getControllerId())) {
                return true;
            }
        }
        return false;
    }

}

class MeliraSylvokOutcastEffect3 extends ContinuousEffectImpl {

    private static FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public MeliraSylvokOutcastEffect3() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.LoseAbility);
        staticText = "Creatures your opponents control lose infect";
    }

    public MeliraSylvokOutcastEffect3(final MeliraSylvokOutcastEffect3 effect) {
        super(effect);
    }

    @Override
    public MeliraSylvokOutcastEffect3 copy() {
        return new MeliraSylvokOutcastEffect3(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            if (opponents.contains(perm.getControllerId())) {
                perm.getAbilities().remove(InfectAbility.getInstance());
            }
        }
        return true;
    }

}
