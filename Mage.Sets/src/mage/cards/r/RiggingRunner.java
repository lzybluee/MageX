
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.watchers.common.PlayerAttackedWatcher;

/**
 *
 * @author LevelX2
 */
public final class RiggingRunner extends CardImpl {

    public RiggingRunner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Raid — Rigging Runner enters the battlefield with a +1/+1 counter on it if you attacked with a creature this turn.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(1), false),
                RaidCondition.instance,
                "<i>Raid</i> &mdash; {this} enters the battlefield with a +1/+1 counter on it if you attacked with a creature this turn.",
                "{this} enters the battlefield with a +1/+1 counter"),
                new PlayerAttackedWatcher());
    }

    public RiggingRunner(final RiggingRunner card) {
        super(card);
    }

    @Override
    public RiggingRunner copy() {
        return new RiggingRunner(this);
    }
}
