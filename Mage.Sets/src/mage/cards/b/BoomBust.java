
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.TargetPermanent;

public final class BoomBust extends SplitCard {

    private static final FilterLandPermanent filter1 = new FilterLandPermanent("land you control");
    private static final FilterLandPermanent filter2 = new FilterLandPermanent("land you don't control");

    static {
        filter1.add(new ControllerPredicate(TargetController.YOU));
        filter2.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public BoomBust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}", "{5}{R}", SpellAbilityType.SPLIT);

        // Boom
        // Destroy target land you control and target land you don't control.
        Effect effect = new DestroyTargetEffect(false, true);
        effect.setText("Destroy target land you control and target land you don't control");
        getLeftHalfCard().getSpellAbility().addEffect(effect);
        getLeftHalfCard().getSpellAbility().addTarget(new TargetPermanent(filter1));
        getLeftHalfCard().getSpellAbility().addTarget(new TargetPermanent(filter2));

        // Bust
        // Destroy all lands.
        getRightHalfCard().getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_LANDS));

    }

    public BoomBust(final BoomBust card) {
        super(card);
    }

    @Override
    public BoomBust copy() {
        return new BoomBust(this);
    }
}
