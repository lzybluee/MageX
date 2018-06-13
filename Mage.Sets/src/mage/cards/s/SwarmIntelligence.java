
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterInstantOrSorcerySpell;

/**
 *
 * @author LevelX2
 */
public final class SwarmIntelligence extends CardImpl {

    public SwarmIntelligence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{6}{U}");

        // Whenever you cast an instant or sorcery spell, you may copy that spell. You may choose new targets for the copy.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CopyTargetSpellEffect(true).setText("you may copy that spell. You may choose new targets for the copy"), new FilterInstantOrSorcerySpell("an instant or sorcery spell"), true, true));
    }

    public SwarmIntelligence(final SwarmIntelligence card) {
        super(card);
    }

    @Override
    public SwarmIntelligence copy() {
        return new SwarmIntelligence(this);
    }
}
