
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author markedagain
 */
public final class SteelyResolve extends CardImpl {

    public SteelyResolve(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // As Steely Resolve enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.AddAbility)));
        // Creatures of the chosen type have shroud.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(ShroudAbility.getInstance(), Duration.WhileOnBattlefield, new FilterSteelyResolve())));
    }

    public SteelyResolve(final SteelyResolve card) {
        super(card);
    }

    @Override
    public SteelyResolve copy() {
        return new SteelyResolve(this);
    }
}

class FilterSteelyResolve extends FilterCreaturePermanent {

    public FilterSteelyResolve() {
        super("All creatures of the chosen type");
    }

    public FilterSteelyResolve(final FilterSteelyResolve filter) {
        super(filter);
    }

    @Override
    public FilterSteelyResolve copy() {
        return new FilterSteelyResolve(this);
    }

    @Override
    public boolean match(Permanent permanent, UUID sourceId, UUID playerId, Game game) {
        if (super.match(permanent, sourceId, playerId, game)) {
            SubType subType = ChooseCreatureTypeEffect.getChoosenCreatureType(sourceId, game);
            if (subType != null && permanent.hasSubtype(subType, game)) {
                return true;
            }
        }
        return false;
    }

}
