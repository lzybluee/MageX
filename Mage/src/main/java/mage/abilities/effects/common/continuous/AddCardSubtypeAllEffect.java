

package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author Galatolol
 */

public class AddCardSubtypeAllEffect extends ContinuousEffectImpl {

    private static FilterPermanent filter;
    private static SubType addedSubtype;

    public AddCardSubtypeAllEffect(FilterPermanent _filter, SubType _addedSubtype, DependencyType _dependency) {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        filter = _filter;
        staticText = "";
        addedSubtype = _addedSubtype;
        addDependencyType(_dependency);
    }

    public AddCardSubtypeAllEffect(final AddCardSubtypeAllEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            if (perm != null && !perm.hasSubtype(addedSubtype, game)) {
                perm.getSubtype(game).add(addedSubtype);
            }
        }
        return true;
    }

    @Override
    public AddCardSubtypeAllEffect copy() {
        return new AddCardSubtypeAllEffect(this);
    }

}