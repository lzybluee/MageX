
package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToACreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class MephidrossVampire extends CardImpl {

    public MephidrossVampire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.subtype.add(SubType.VAMPIRE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Each creature you control is a Vampire in addition to its other creature types and has "Whenever this creature deals damage to a creature, put a +1/+1 counter on this creature."
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MephidrossVampireEffect()));

    }

    public MephidrossVampire(final MephidrossVampire card) {
        super(card);
    }

    @Override
    public MephidrossVampire copy() {
        return new MephidrossVampire(this);
    }
}

class MephidrossVampireEffect extends ContinuousEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    MephidrossVampireEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.staticText = "Each creature you control is a Vampire in addition to its other creature types and has \"Whenever this creature deals damage to a creature, put a +1/+1 counter on this creature.\"";

        // wait become creature effects first then apply own
        this.addDependedToType(DependencyType.BecomeCreature);
    }

    MephidrossVampireEffect(final MephidrossVampireEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public MephidrossVampireEffect copy() {
        return new MephidrossVampireEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            switch (layer) {
                case AbilityAddingRemovingEffects_6:
                    creature.addAbility(new DealsDamageToACreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, false, false), source.getSourceId(), game);
                    break;
                case TypeChangingEffects_4:
                    if (!creature.hasSubtype(SubType.VAMPIRE, game)) {
                        creature.getSubtype(game).add(SubType.VAMPIRE);
                    }
                    break;
            }
        }
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.TypeChangingEffects_4;
    }
}
