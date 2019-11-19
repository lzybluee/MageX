package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.GoblinToken;

import java.util.UUID;

/**
 * @author North
 */
public final class KrenkoMobBoss extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.GOBLIN, "the number of Goblins you control");
    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public KrenkoMobBoss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {tap}: create X 1/1 red Goblin creature tokens, where X is the number of Goblins you control.
        this.addAbility(new SimpleActivatedAbility(
                new CreateTokenEffect(new GoblinToken(), xValue), new TapSourceCost()
        ));
    }

    private KrenkoMobBoss(final KrenkoMobBoss card) {
        super(card);
    }

    @Override
    public KrenkoMobBoss copy() {
        return new KrenkoMobBoss(this);
    }
}
