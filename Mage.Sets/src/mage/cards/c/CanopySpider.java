
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class CanopySpider extends CardImpl {

    public CanopySpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.SPIDER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        this.addAbility(ReachAbility.getInstance());
    }

    public CanopySpider(final CanopySpider card) {
        super(card);
    }

    @Override
    public CanopySpider copy() {
        return new CanopySpider(this);
    }
}
