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
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.token.SaprolingToken;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class RithTheAwakener extends CardImpl {

    public RithTheAwakener(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Rith, the Awakener deals combat damage to a player, you may pay {2}{G}. If you do, choose a color, then create a 1/1 green Saproling creature token for each permanent of that color.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DoIfCostPaid(new RithTheAwakenerEffect(), new ManaCostsImpl("{2}{G}")), false));
    }

    public RithTheAwakener(final RithTheAwakener card) {
        super(card);
    }

    @Override
    public RithTheAwakener copy() {
        return new RithTheAwakener(this);
    }
}

class RithTheAwakenerEffect extends OneShotEffect {

    public RithTheAwakenerEffect() {
        super(Outcome.Benefit);
        this.staticText = "choose a color, then create a 1/1 green Saproling creature token for each permanent of that color";
    }

    public RithTheAwakenerEffect(final RithTheAwakenerEffect effect) {
        super(effect);
    }

    @Override
    public RithTheAwakenerEffect copy() {
        return new RithTheAwakenerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        ChoiceColor choice = new ChoiceColor();
        if (controller.choose(outcome, choice, game)) {
            game.informPlayers(new StringBuilder(controller.getLogName()).append(" chooses ").append(choice.getColor()).toString());
            FilterPermanent filter = new FilterPermanent();
            filter.add(new ColorPredicate(choice.getColor()));
            int cardsWithColor = game.getBattlefield().count(filter, source.getSourceId(), controller.getId(), game);
            if (cardsWithColor > 0) {
                new CreateTokenEffect(new SaprolingToken(), cardsWithColor).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
