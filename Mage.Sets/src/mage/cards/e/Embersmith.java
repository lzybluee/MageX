
package mage.cards.e;

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
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

/**
 * @author Loki, North
 */
public final class Embersmith extends CardImpl {
    public Embersmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        FilterArtifactSpell filter = new FilterArtifactSpell("an artifact spell");
        SpellCastControllerTriggeredAbility ability = new SpellCastControllerTriggeredAbility(new EmbersmithEffect(), filter, false);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    public Embersmith(final Embersmith card) {
        super(card);
    }

    @Override
    public Embersmith copy() {
        return new Embersmith(this);
    }
}

class EmbersmithEffect extends OneShotEffect {
    EmbersmithEffect() {
        super(Outcome.Damage);
        staticText =  "you may pay {1}. If you do, {this} deals 1 damage to any target";
    }

    EmbersmithEffect(final EmbersmithEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cost cost = new GenericManaCost(1);
        cost.clearPaid();
        if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false, null)) {
            Permanent permanent = game.getPermanent(source.getFirstTarget());
            if (permanent != null) {
                permanent.damage(1, source.getSourceId(), game, false, true);
                return true;
            }
            Player player = game.getPlayer(source.getFirstTarget());
            if (player != null) {
                player.damage(1, source.getSourceId(), game, false, true);
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public EmbersmithEffect copy() {
        return new EmbersmithEffect(this);
    }
}