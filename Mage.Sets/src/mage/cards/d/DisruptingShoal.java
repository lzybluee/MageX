/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIAB8LE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.cards.d;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class DisruptingShoal extends CardImpl {

    public DisruptingShoal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{U}{U}");
        this.subtype.add(SubType.ARCANE);

        // You may exile a blue card with converted mana cost X from your hand rather than pay Disrupting Shoal's mana cost.
        FilterOwnedCard filter = new FilterOwnedCard("a blue card with converted mana cost X from your hand");
        filter.add(new ColorPredicate(ObjectColor.BLUE));
        filter.add(Predicates.not(new CardIdPredicate(this.getId()))); // the exile cost can never be paid with the card itself
        this.addAbility(new AlternativeCostSourceAbility(new ExileFromHandCost(new TargetCardInHand(filter), true)));

        // 2/1/2005: Disrupting Shoal can target any spell, but does nothing unless that spell's converted mana cost is X.
        // Counter target spell if its converted mana cost is X.
        this.getSpellAbility().addEffect(new DisruptingShoalCounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    public DisruptingShoal(final DisruptingShoal card) {
        super(card);
    }

    @Override
    public DisruptingShoal copy() {
        return new DisruptingShoal(this);
    }
}

class DisruptingShoalCounterTargetEffect extends OneShotEffect {

    public DisruptingShoalCounterTargetEffect() {
        super(Outcome.Detriment);
    }

    public DisruptingShoalCounterTargetEffect(final DisruptingShoalCounterTargetEffect effect) {
        super(effect);
    }

    @Override
    public DisruptingShoalCounterTargetEffect copy() {
        return new DisruptingShoalCounterTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        if (spell != null && isConvertedManaCostEqual(source, spell.getConvertedManaCost())) {
            return game.getStack().counter(source.getFirstTarget(), source.getSourceId(), game);
        }
        return false;
    }

    private boolean isConvertedManaCostEqual(Ability sourceAbility, int amount) {
        for (Cost cost : sourceAbility.getCosts()) {
            if (cost.isPaid() && cost instanceof ExileFromHandCost) {
                for (Card card : ((ExileFromHandCost) cost).getCards()) {
                    if (card instanceof SplitCard) {
                        if (((SplitCard) card).getLeftHalfCard().getConvertedManaCost() == amount) {
                            return true;
                        }
                        if (((SplitCard) card).getRightHalfCard().getConvertedManaCost() == amount) {
                            return true;
                        }
                    } else if (card.getConvertedManaCost() == amount) {
                        return true;
                    }
                }
                return false;
            }
        }
        // No alternate costs payed so compare to X value
        return sourceAbility.getManaCostsToPay().getX() == amount;
    }

    @Override
    public String getText(Mode mode) {
        return "Counter target spell if its converted mana cost is X";
    }

}
