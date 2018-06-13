
package mage.cards.i;

import java.util.UUID;
import mage.Mana;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author North
 */
public final class InfernalPlunge extends CardImpl {

    public InfernalPlunge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // As an additional cost to cast Infernal Plunge, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        // Add {R}{R}{R}.
        this.getSpellAbility().addEffect(new BasicManaEffect(Mana.RedMana(3)));
    }

    public InfernalPlunge(final InfernalPlunge card) {
        super(card);
    }

    @Override
    public InfernalPlunge copy() {
        return new InfernalPlunge(this);
    }
}
