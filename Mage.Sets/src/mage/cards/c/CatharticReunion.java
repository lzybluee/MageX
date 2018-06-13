
package mage.cards.c;

import java.util.UUID;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author fireshoes
 */
public final class CatharticReunion extends CardImpl {

    public CatharticReunion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");

        // As an additional cost to cast Cathartic Reunion, discard two cards.
        this.getSpellAbility().addCost(new DiscardTargetCost(new TargetCardInHand(2, new FilterCard("two cards"))));

        // Draw three cards.
        getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
    }

    public CatharticReunion(final CatharticReunion card) {
        super(card);
    }

    @Override
    public CatharticReunion copy() {
        return new CatharticReunion(this);
    }
}
