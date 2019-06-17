
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
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
 * @author LevelX2
 */
public final class MarduSkullhunter extends CardImpl {

    public MarduSkullhunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Mardu Skullhunter enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // <em>Raid</em> - When Mardu Skullhunter enters the battlefield, if you attacked with a creature this turn, target opponent discards a card.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(new EntersBattlefieldTriggeredAbility(new DiscardTargetEffect(1)), RaidCondition.instance,
                "<i>Raid</i> &mdash; When {this} enters the battlefield, if you attacked with a creature this turn, target opponent discards a card.");
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability, new PlayerAttackedWatcher());
    }

    public MarduSkullhunter(final MarduSkullhunter card) {
        super(card);
    }

    @Override
    public MarduSkullhunter copy() {
        return new MarduSkullhunter(this);
    }
}
