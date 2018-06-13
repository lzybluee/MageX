
package mage.cards.l;

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
import mage.cards.s.SilverpeltWerewolf;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author Loki
 */
public final class LambholtElder extends CardImpl {

    public LambholtElder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        this.transformable = true;
        this.secondSideCardClazz = SilverpeltWerewolf.class;

        // At the beginning of each upkeep, if no spells were cast last turn, transform Lambholt Elder.
        this.addAbility(new TransformAbility());
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(true), TargetController.ANY, false);
        this.addAbility(new ConditionalTriggeredAbility(ability, NoSpellsWereCastLastTurnCondition.instance, TransformAbility.NO_SPELLS_TRANSFORM_RULE));
    }

    public LambholtElder(final LambholtElder card) {
        super(card);
    }

    @Override
    public LambholtElder copy() {
        return new LambholtElder(this);
    }
}
