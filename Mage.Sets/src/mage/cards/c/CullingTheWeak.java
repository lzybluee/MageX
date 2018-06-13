
package mage.cards.c;

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
 * @author emerald000
 */
public final class CullingTheWeak extends CardImpl {

    public CullingTheWeak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // As an additional cost to cast Culling the Weak, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));

        // Add {B}{B}{B}{B}.
        this.getSpellAbility().addEffect(new BasicManaEffect(Mana.BlackMana(4)));
    }

    public CullingTheWeak(final CullingTheWeak card) {
        super(card);
    }

    @Override
    public CullingTheWeak copy() {
        return new CullingTheWeak(this);
    }
}
