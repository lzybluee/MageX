
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class PublicExecution extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");
    
    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public PublicExecution(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{5}{B}");

        // Destroy target creature an opponent controls. Each other creature that player controls gets -2/-0 until end of turn.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new PublicExecutionEffect());
    }

    public PublicExecution(final PublicExecution card) {
        super(card);
    }

    @Override
    public PublicExecution copy() {
        return new PublicExecution(this);
    }
}

class PublicExecutionEffect extends OneShotEffect {
    
    public PublicExecutionEffect() {
        super(Outcome.Benefit);
        staticText = "Each other creature that player controls gets -2/-0 until end of turn";
    }

    public PublicExecutionEffect(final PublicExecutionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = (Permanent) game.getLastKnownInformation(source.getFirstTarget(), Zone.BATTLEFIELD);
        if (target != null) {
            UUID opponent = target.getControllerId();
            if (opponent != null) {
                FilterCreaturePermanent filter = new FilterCreaturePermanent("each other creature that player controls");
                filter.add(new ControllerIdPredicate(opponent));
                filter.add(Predicates.not(new PermanentIdPredicate(target.getId())));
                ContinuousEffect effect = new BoostAllEffect(-2,0, Duration.EndOfTurn, filter, false);
                game.addEffect(effect, source);
                return true;
            }
        }
        return false;
    }

    @Override
    public PublicExecutionEffect copy() {
        return new PublicExecutionEffect(this);
    }
}
