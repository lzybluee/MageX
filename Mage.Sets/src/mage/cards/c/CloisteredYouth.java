
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.u.UnholyFiend;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author Loki
 */
public final class CloisteredYouth extends CardImpl {

    public CloisteredYouth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.transformable = true;
        this.secondSideCardClazz = UnholyFiend.class;

        // At the beginning of your upkeep, you may transform Cloistered Youth.
        this.addAbility(new TransformAbility());
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(true), TargetController.YOU, true));
    }

    public CloisteredYouth(final CloisteredYouth card) {
        super(card);
    }

    @Override
    public CloisteredYouth copy() {
        return new CloisteredYouth(this);
    }
}
