
package mage.cards.m;

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
import mage.cards.t.TovolarsMagehunter;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author North
 */
public final class MondronenShaman extends CardImpl {

    public MondronenShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.HUMAN);        
        this.subtype.add(SubType.SHAMAN);
        this.subtype.add(SubType.WEREWOLF);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        this.transformable = true;
        this.secondSideCardClazz = TovolarsMagehunter.class;

        // At the beginning of each upkeep, if no spells were cast last turn, transform Mondronen Shaman.
        this.addAbility(new TransformAbility());
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(true), TargetController.ANY, false);
        this.addAbility(new ConditionalTriggeredAbility(ability,
                NoSpellsWereCastLastTurnCondition.instance,
                TransformAbility.NO_SPELLS_TRANSFORM_RULE));
    }

    public MondronenShaman(final MondronenShaman card) {
        super(card);
    }

    @Override
    public MondronenShaman copy() {
        return new MondronenShaman(this);
    }
}
