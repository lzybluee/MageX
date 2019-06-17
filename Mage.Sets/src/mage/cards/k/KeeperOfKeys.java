
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MonarchIsSourceControllerCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.combat.CantBeBlockedAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class KeeperOfKeys extends CardImpl {

    public KeeperOfKeys(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Keeper of Keys enters the battlefield, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect(), false));

        // At the beginning of your upkeep, if you're the monarch, creatures you control can't be blocked this turn.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD,
                new CantBeBlockedAllEffect(new FilterControlledCreaturePermanent("creatures you control"), Duration.EndOfTurn),
                TargetController.YOU, false), MonarchIsSourceControllerCondition.instance,
                "At the beginning of your upkeep, if you're the monarch, creatures you control can't be blocked this turn."));
    }

    public KeeperOfKeys(final KeeperOfKeys card) {
        super(card);
    }

    @Override
    public KeeperOfKeys copy() {
        return new KeeperOfKeys(this);
    }
}
