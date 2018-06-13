
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.MeldCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.EldraziHorrorToken;

/**
 *
 * @author LevelX2
 */
public final class HanweirTheWrithingTownship extends MeldCard {
    public HanweirTheWrithingTownship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.OOZE);
        this.power = new MageInt(7);
        this.toughness = new MageInt(4);

        this.nightCard = true;// Meld card

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());
        
        // Whenever Hanweir, the Writhing Township attacks, create two 3/2 colorless Eldrazi Horror creature tokens tapped and attacking.
        this.addAbility(new AttacksTriggeredAbility(new CreateTokenEffect(new EldraziHorrorToken(), 2, true, true), false));
    }

    public HanweirTheWrithingTownship(final HanweirTheWrithingTownship card) {
        super(card);
    }

    @Override
    public HanweirTheWrithingTownship copy() {
        return new HanweirTheWrithingTownship(this);
    }
}
