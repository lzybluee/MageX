
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BlocksTriggeredAbility;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class AlabornZealot extends CardImpl {

    public AlabornZealot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Alaborn Zealot blocks a creature, destroy that creature and Alaborn Zealot.
        Ability ability = new BlocksTriggeredAbility(new DestroyTargetEffect().setText("destroy that creature"), false, true, true);
        ability.addEffect(new DestroySourceEffect().setText("and {this}"));
        this.addAbility(ability);
    }

    public AlabornZealot(final AlabornZealot card) {
        super(card);
    }

    @Override
    public AlabornZealot copy() {
        return new AlabornZealot(this);
    }
}
