
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class GravebladeMarauder extends CardImpl {

    public GravebladeMarauder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Graveblade Marauder deals combat damage to a player, that player loses life equal to the number of creature cards in your graveyard.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new GravebladeMarauderEffect(), false, true));
    }

    public GravebladeMarauder(final GravebladeMarauder card) {
        super(card);
    }

    @Override
    public GravebladeMarauder copy() {
        return new GravebladeMarauder(this);
    }
}

class GravebladeMarauderEffect extends OneShotEffect {

    public GravebladeMarauderEffect() {
        super(Outcome.LoseLife);
        this.staticText = "that player loses life equal to the number of creature cards in your graveyard";
    }

    public GravebladeMarauderEffect(final GravebladeMarauderEffect effect) {
        super(effect);
    }

    @Override
    public GravebladeMarauderEffect copy() {
        return new GravebladeMarauderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (targetPlayer != null && controller != null) {
            targetPlayer.loseLife(controller.getGraveyard().count(new FilterCreatureCard(), game), game, false);
            return true;
        }
        return false;
    }
}
