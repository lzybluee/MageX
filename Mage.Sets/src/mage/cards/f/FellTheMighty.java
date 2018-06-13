
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class FellTheMighty extends CardImpl {

    public FellTheMighty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}");

        // Destroy all creatures with power greater than target creature's power.
        this.getSpellAbility().addEffect(new FellTheMightyEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    public FellTheMighty(final FellTheMighty card) {
        super(card);
    }

    @Override
    public FellTheMighty copy() {
        return new FellTheMighty(this);
    }
}

class FellTheMightyEffect extends OneShotEffect {

    public FellTheMightyEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy all creatures with power greater than target creature's power";
    }

    public FellTheMightyEffect(final FellTheMightyEffect effect) {
        super(effect);
    }

    @Override
    public FellTheMightyEffect copy() {
        return new FellTheMightyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent targetCreature = game.getPermanentOrLKIBattlefield(source.getFirstTarget());
        if (controller != null && targetCreature != null) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, controller.getId(), source.getSourceId(), game)) {
                if (permanent.getPower().getValue() > targetCreature.getPower().getValue()) {
                    permanent.destroy(source.getSourceId(), game, false);
                }
            }
            return true;
        }
        return false;
    }
}
