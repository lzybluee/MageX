
package mage.cards.c;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public final class CommandBeacon extends CardImpl {

    public CommandBeacon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {T}, Sacrifice Command Beacon: Put your commander into your hand from the command zone.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CommandBeaconEffect(), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public CommandBeacon(final CommandBeacon card) {
        super(card);
    }

    @Override
    public CommandBeacon copy() {
        return new CommandBeacon(this);
    }
}

class CommandBeaconEffect extends OneShotEffect {

    CommandBeaconEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Put your commander into your hand from the command zone";
    }

    CommandBeaconEffect(final CommandBeaconEffect effect) {
        super(effect);
    }

    @Override
    public CommandBeaconEffect copy() {
        return new CommandBeaconEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            List<Card> commandersInCommandZone = new ArrayList<>(1);
            for (UUID commanderId : controller.getCommandersIds()) {
                Card commander = game.getCard(commanderId);
                if (commander != null && game.getState().getZone(commander.getId()) == Zone.COMMAND) {
                    commandersInCommandZone.add(commander);
                }
            }
            if (commandersInCommandZone.size() == 1) {
                controller.moveCards(commandersInCommandZone.get(0), Zone.HAND, source, game);
            }
            else if (commandersInCommandZone.size() == 2) {
                Card firstCommander = commandersInCommandZone.get(0);
                Card secondCommander = commandersInCommandZone.get(1);
                if (controller.chooseUse(Outcome.ReturnToHand, "Return which commander to hand?", null, firstCommander.getName(), secondCommander.getName(), source, game)) {
                    controller.moveCards(firstCommander, Zone.HAND, source, game);
                }
                else {
                    controller.moveCards(secondCommander, Zone.HAND, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
