
package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.SpliceOntoArcaneAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class GoryosVengeance extends CardImpl {

    private static final FilterCard filter = new FilterCard("legendary creature card");

    static {
        filter.add(new SupertypePredicate(SuperType.LEGENDARY));
    }

    public GoryosVengeance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}");
        this.subtype.add(SubType.ARCANE);

        // Return target legendary creature card from your graveyard to the battlefield. That creature gains haste. Exile it at the beginning of the next end step.
        this.getSpellAbility().addEffect(new GoryosVengeanceEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));

        // Splice onto Arcane {2}{B}
        this.addAbility(new SpliceOntoArcaneAbility("{2}{B}"));
    }

    public GoryosVengeance(final GoryosVengeance card) {
        super(card);
    }

    @Override
    public GoryosVengeance copy() {
        return new GoryosVengeance(this);
    }
}

class GoryosVengeanceEffect extends OneShotEffect {

    public GoryosVengeanceEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Return target legendary creature card from your graveyard to the battlefield. That creature gains haste. Exile it at the beginning of the next end step";
    }

    public GoryosVengeanceEffect(final GoryosVengeanceEffect effect) {
        super(effect);
    }

    @Override
    public GoryosVengeanceEffect copy() {
        return new GoryosVengeanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(targetPointer.getFirst(game, source));
            if (card != null) {
                if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                    Permanent permanent = game.getPermanent(card.getId());
                    if (permanent != null) {
                        // Haste
                        ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
                        effect.setTargetPointer(new FixedTarget(permanent, game));
                        game.addEffect(effect, source);
                        // Exile it at end of turn
                        Effect exileEffect = new ExileTargetEffect("Exile " + permanent.getName() + " at the beginning of the next end step");
                        exileEffect.setTargetPointer(new FixedTarget(permanent, game));
                        DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect);
                        game.addDelayedTriggeredAbility(delayedAbility, source);
                        return true;

                    }
                }
            }
        }
        return false;
    }
}
