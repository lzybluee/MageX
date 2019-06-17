
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.TwoOrMoreSpellsWereCastLastTurnCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 * @author nantuko
 */
public final class MercilessPredator extends CardImpl {

    public MercilessPredator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.WEREWOLF);
        this.color.setRed(true);

        // this card is the second face of double-faced card
        this.nightCard = true;
        this.transformable = true;

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Merciless Predator.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(false), TargetController.ANY, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, TwoOrMoreSpellsWereCastLastTurnCondition.instance, TransformAbility.TWO_OR_MORE_SPELLS_TRANSFORM_RULE));
    }

    public MercilessPredator(final MercilessPredator card) {
        super(card);
    }

    @Override
    public MercilessPredator copy() {
        return new MercilessPredator(this);
    }
}
