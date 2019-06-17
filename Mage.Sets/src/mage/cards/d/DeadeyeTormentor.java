
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;
import mage.watchers.common.PlayerAttackedWatcher;

/**
 *
 * @author TheElk801
 */
public final class DeadeyeTormentor extends CardImpl {

    public DeadeyeTormentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // <i>Raid</i> &mdash; When Deadeye Tormentor enters the battlefield, if you attacked with a creature this turn, target opponent discards a card.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(new EntersBattlefieldTriggeredAbility(new DiscardTargetEffect(1)), RaidCondition.instance,
                "<i>Raid</i> &mdash; When {this} enters the battlefield, if you attacked with a creature this turn, target opponent discards a card.");
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability, new PlayerAttackedWatcher());
    }

    public DeadeyeTormentor(final DeadeyeTormentor card) {
        super(card);
    }

    @Override
    public DeadeyeTormentor copy() {
        return new DeadeyeTormentor(this);
    }
}
