
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author spjspj
 */
public final class HungryHungryHeifer extends CardImpl {

    public HungryHungryHeifer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.COW);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your upkeep, you may remove a counter from a permanent you control. If you don't, sacrifice Hungry Hungry Heifer.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new HungryHungryHeiferEffect(), TargetController.YOU, false, false));
    }

    public HungryHungryHeifer(final HungryHungryHeifer card) {
        super(card);
    }

    @Override
    public HungryHungryHeifer copy() {
        return new HungryHungryHeifer(this);
    }
}

class HungryHungryHeiferEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a permanent you control with a counter on it");

    static {
        filter.add(new CounterPredicate(null));
    }

    public HungryHungryHeiferEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "you may remove a counter from a permanent you control. If you don't, sacrifice {this}";
    }

    public HungryHungryHeiferEffect(final HungryHungryHeiferEffect effect) {
        super(effect);
    }

    @Override
    public HungryHungryHeiferEffect copy() {
        return new HungryHungryHeiferEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceObject = (Permanent) source.getSourceObjectIfItStillExists(game);
        if (sourceObject != null && controller != null) {
            if (controller.chooseUse(outcome, "Remove a counter from a permanent you control?", source, game)) {
                TargetControlledPermanent target = new TargetControlledPermanent(1, 1, filter, true);
                RemoveCounterCost cost = new RemoveCounterCost(target);
                if (cost.pay(null, game, source.getSourceId(), controller.getId(), true)) {
                    return true;
                }
            }
            sourceObject.sacrifice(source.getSourceId(), game);
            return true;
        }
        return false;
    }
}
