
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.NoSpellsWereCastLastTurnCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.n.NeckBreaker;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author fireshoes
 */
public final class BreakneckRider extends CardImpl {

    public BreakneckRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}");
        this.subtype.add(SubType.HUMAN, SubType.SCOUT, SubType.WEREWOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        
        this.transformable = true;
        this.secondSideCardClazz = NeckBreaker.class;

        // At the beginning of each upkeep, if no spells were cast last turn, transform Breakneck Rider.
        this.addAbility(new TransformAbility());
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(true), TargetController.ANY, false);
        this.addAbility(new ConditionalTriggeredAbility(ability, NoSpellsWereCastLastTurnCondition.instance, TransformAbility.NO_SPELLS_TRANSFORM_RULE));
    }

    public BreakneckRider(final BreakneckRider card) {
        super(card);
    }

    @Override
    public BreakneckRider copy() {
        return new BreakneckRider(this);
    }
}
