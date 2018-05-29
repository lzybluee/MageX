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
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
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
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

/**
 *
 * @author Quercitron
 */
public final class Flash extends CardImpl {

    public Flash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // You may put a creature card from your hand onto the battlefield. If you do, sacrifice it unless you pay its mana cost reduced by up to {2}.
        this.getSpellAbility().addEffect(new FlashEffect());
    }

    public Flash(final Flash card) {
        super(card);
    }

    @Override
    public Flash copy() {
        return new Flash(this);
    }
}

class FlashEffect extends OneShotEffect {

    private static final String choiceText = "Put a creature card from your hand onto the battlefield?";

    public FlashEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "You may put a creature card from your hand onto the battlefield. If you do, sacrifice it unless you pay its mana cost reduced by up to {2}.";
    }

    public FlashEffect(final FlashEffect effect) {
        super(effect);
    }

    @Override
    public FlashEffect copy() {
        return new FlashEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || !controller.chooseUse(Outcome.PutCreatureInPlay, choiceText, source, game)) {
            return false;
        }

        TargetCardInHand target = new TargetCardInHand(new FilterCreatureCard());
        if (controller.choose(Outcome.PutCreatureInPlay, target, source.getSourceId(), game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                ManaCosts<ManaCost> reducedCost = ManaCosts.removeVariableManaCost(CardUtil.reduceCost(card.getManaCost(), 2));
                if (controller.chooseUse(Outcome.Benefit, String.valueOf("Pay " + reducedCost.getText()) + Character.toString('?'), source, game)) {
                    reducedCost.clearPaid();
                    if (reducedCost.pay(source, game, source.getSourceId(), source.getControllerId(), false, null)) {
                        return true;
                    }
                }

                Permanent permanent = game.getPermanent(card.getId());
                if (permanent != null) {
                    permanent.sacrifice(source.getSourceId(), game);
                }

                return true;
            }
        }
        return false;
    }

}
