
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageAndDiedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Alvin
 */
public final class AbattoirGhoul extends CardImpl {

    public AbattoirGhoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever a creature dealt damage by Abattoir Ghoul this turn dies, you gain life equal to that creature's toughness.
        this.addAbility(new DealtDamageAndDiedTriggeredAbility(new AbattoirGhoulEffect(), false));
    }

    public AbattoirGhoul(final AbattoirGhoul card) {
        super(card);
    }

    @Override
    public AbattoirGhoul copy() {
        return new AbattoirGhoul(this);
    }
}

class AbattoirGhoulEffect extends OneShotEffect {

    public AbattoirGhoulEffect() {
        super(Outcome.GainLife);
        staticText = "you gain life equal to that creature's toughness";
    }

    public AbattoirGhoulEffect(final AbattoirGhoulEffect effect) {
        super(effect);
    }

    @Override
    public AbattoirGhoulEffect copy() {
        return new AbattoirGhoulEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Card card = game.getCard(targetPointer.getFirst(game, source));
        if (card != null) {
            Permanent creature = (Permanent) game.getLastKnownInformation(card.getId(), Zone.BATTLEFIELD);
            if (creature != null) {
                int toughness = creature.getToughness().getValue();
                if (you != null) {
                    you.gainLife(toughness, game, source);
                    return true;
                }
            }
        }
        return false;
    }
}