
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MonstrosityAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class StormbreathDragon extends CardImpl {

    public StormbreathDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Haste
        this.addAbility(HasteAbility.getInstance());
        // protection from white
        this.addAbility(ProtectionAbility.from(ObjectColor.WHITE));
        // {5}{R}{R}: Monstrosity 3.
        this.addAbility(new MonstrosityAbility("{5}{R}{R}",3));
        // When Stormbreath Dragon becomes monstrous, it deals damage to each opponent equal to the number of cards in that player's hand.
        this.addAbility(new BecomesMonstrousSourceTriggeredAbility(new StormbreathDragonDamageEffect()));
    }

    public StormbreathDragon(final StormbreathDragon card) {
        super(card);
    }

    @Override
    public StormbreathDragon copy() {
        return new StormbreathDragon(this);
    }
}

class StormbreathDragonDamageEffect extends OneShotEffect {

    public StormbreathDragonDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "it deals damage to each opponent equal to the number of cards in that player's hand";
    }

    public StormbreathDragonDamageEffect(final StormbreathDragonDamageEffect effect) {
        super(effect);
    }

    @Override
    public StormbreathDragonDamageEffect copy() {
        return new StormbreathDragonDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null) {
                int damage = opponent.getHand().size();
                if (damage > 0) {
                    opponent.damage(damage, source.getSourceId(), game, false, true);
                }
            }
        }
        return true;
    }
}