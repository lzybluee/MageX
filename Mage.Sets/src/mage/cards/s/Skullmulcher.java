
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.DevouredCreaturesCount;
import mage.abilities.effects.common.DevourEffect.DevourFactor;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.DevourAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class Skullmulcher extends CardImpl {

    public Skullmulcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Devour 1 (As this enters the battlefield, you may sacrifice any number of creatures. This creature enters the battlefield with twice that many +1/+1 counters on it.)
        this.addAbility(new DevourAbility(DevourFactor.Devour1));

        // When Skullmulcher enters the battlefield, draw a card for each creature it devoured.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DrawCardSourceControllerEffect(new DevouredCreaturesCount()),false));
    }

    public Skullmulcher(final Skullmulcher card) {
        super(card);
    }

    @Override
    public Skullmulcher copy() {
        return new Skullmulcher(this);
    }
}
