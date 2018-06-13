
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.common.FilterArtifactSpell;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Loki
 */
public final class Lifesmith extends CardImpl {
    public Lifesmith (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        FilterArtifactSpell filter = new FilterArtifactSpell("an artifact spell");
        this.addAbility(new SpellCastControllerTriggeredAbility(new LifesmithEffect(), filter, false));
    }

    public Lifesmith (final Lifesmith card) {
        super(card);
    }

    @Override
    public Lifesmith copy() {
        return new Lifesmith(this);
    }
}

class LifesmithEffect extends OneShotEffect {
    LifesmithEffect() {
        super(Outcome.GainLife);
        staticText = "you may pay {1}. If you do, you gain 3 life";
    }

    LifesmithEffect(final LifesmithEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cost cost = new GenericManaCost(1);
        cost.clearPaid();
        if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false, null)) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                player.gainLife(3, game, source);
                return true;
            }
        }
        return false;
    }

    @Override
    public LifesmithEffect copy() {
        return new LifesmithEffect(this);
    }
}