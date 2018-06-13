
package mage.cards.i;
 
import java.util.UUID;
import mage.abilities.effects.common.DetainTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetCreaturePermanent;
 
/**
 *
 * @author LevelX2
 */
public final class InactionInjunction extends CardImpl {
 
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");
 
    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }
 
    public InactionInjunction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{U}");
 

 
        // Detain target creature an opponent controls.
        // (Until your next turn, that creature can't attack or block and its activated abilities can't be activated.)
        this.getSpellAbility().addEffect(new DetainTargetEffect());
        TargetCreaturePermanent target = new TargetCreaturePermanent(filter);
        this.getSpellAbility().addTarget(target);
        
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
 
    }
 
    public InactionInjunction(final InactionInjunction card) {
        super(card);
    }
 
    @Override
    public InactionInjunction copy() {
        return new InactionInjunction(this);
    }
}