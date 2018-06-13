
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterArtifactSpell;
import mage.game.Game;
import mage.game.permanent.token.MyrToken;

/**
 *
 * @author Loki, North
 */
public final class Myrsmith extends CardImpl {

    public Myrsmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you cast an artifact spell, you may pay {1}. If you do, put a 1/1 colorless Myr artifact creature token onto the battlefield.
        FilterArtifactSpell filter = new FilterArtifactSpell("an artifact spell");
        this.addAbility(new SpellCastControllerTriggeredAbility(new MyrsmithEffect(), filter, false));
    }

    public Myrsmith(final Myrsmith card) {
        super(card);
    }

    @Override
    public Myrsmith copy() {
        return new Myrsmith(this);
    }
}

class MyrsmithEffect extends CreateTokenEffect {

    public MyrsmithEffect() {
        super(new MyrToken());
        staticText = "you may pay {1}. If you do, create a 1/1 colorless Myr artifact creature token";
    }

    public MyrsmithEffect(final MyrsmithEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cost cost = new GenericManaCost(1);
        cost.clearPaid();
        if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false, null)) {
            super.apply(game, source);
        }
        return true;
    }

    @Override
    public MyrsmithEffect copy() {
        return new MyrsmithEffect(this);
    }
}
