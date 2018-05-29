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
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public final class MysticMeditation extends CardImpl {

    public MysticMeditation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}");

        // Draw three cards. Then discard two cards unless you discard a creature card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
        this.getSpellAbility().addEffect(new MysticMeditationEffect());

    }

    public MysticMeditation(final MysticMeditation card) {
        super(card);
    }

    @Override
    public MysticMeditation copy() {
        return new MysticMeditation(this);
    }
}

class MysticMeditationEffect extends OneShotEffect {

    public MysticMeditationEffect() {
        super(Outcome.Damage);
        staticText = "Then discard two cards unless you discard a creature card";
    }

    public MysticMeditationEffect(final MysticMeditationEffect effect) {
        super(effect);
    }

    @Override
    public MysticMeditationEffect copy() {
        return new MysticMeditationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        FilterCard filter = new FilterCard("creature card to discard");
        filter.add(new CardTypePredicate(CardType.CREATURE));
        if (controller != null
                && controller.getHand().count(filter, game) > 0
                && controller.chooseUse(Outcome.Discard, "Do you want to discard a creature card?  If you don't, you must discard 2 cards", source, game)) {
            Cost cost = new DiscardTargetCost(new TargetCardInHand(filter));
            if (cost.canPay(source, source.getSourceId(), controller.getId(), game)) {
                if (cost.pay(source, game, source.getSourceId(), controller.getId(), false, null)) {
                    return true;
                }
            }
        }
        if (controller != null) {
            controller.discard(2, false, source, game);
            return true;
        }
        return false;
    }
}