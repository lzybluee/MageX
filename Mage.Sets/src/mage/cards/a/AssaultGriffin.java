

package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class AssaultGriffin extends CardImpl {

    public AssaultGriffin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");

        this.subtype.add(SubType.GRIFFIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        this.addAbility(FlyingAbility.getInstance());
    }

    public AssaultGriffin(final AssaultGriffin card) {
        super(card);
    }

    @Override
    public AssaultGriffin copy() {
        return new AssaultGriffin(this);
    }

}

