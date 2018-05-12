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
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class SinProdder extends CardImpl {

    public SinProdder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.DEVIL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility());

        // At the beginning of your upkeep, reveal the top card of your library. Any opponent may have you put that card into your graveyard. If a player does,
        // Sin Prodder deals damage to that player equal to that card's converted mana cost. Otherwise, put that card into your hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SinProdderEffect(), TargetController.YOU, false));
    }

    public SinProdder(final SinProdder card) {
        super(card);
    }

    @Override
    public SinProdder copy() {
        return new SinProdder(this);
    }
}

class SinProdderEffect extends OneShotEffect {

    public SinProdderEffect() {
        super(Outcome.DrawCard);
        this.staticText = "reveal the top card of your library. Any opponent may have you put that card into your graveyard. If a player does, "
                + "{this} deals damage to that player equal to that card's converted mana cost. Otherwise, put that card into your hand";
    }

    public SinProdderEffect(final SinProdderEffect effect) {
        super(effect);
    }

    @Override
    public SinProdderEffect copy() {
        return new SinProdderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                controller.revealCards(source, new CardsImpl(card), game);
                String message = "Put " + card.getName() + " in " + controller.getName() + "'s graveyard?";
                boolean putInGraveyard = false;
                for (UUID opponentUuid : game.getOpponents(source.getControllerId())) {
                    Player opponent = game.getPlayer(opponentUuid);
                    if (opponent != null && !putInGraveyard && opponent.chooseUse(Outcome.Damage, message, source, game)) {
                        putInGraveyard = true;
                        opponent.damage(card.getConvertedManaCost(), source.getSourceId(), game, false, true);
                        // 4/8/2016: Each opponent in turn order, starting with the one after you in turn order, may choose to have you put that card into your graveyard.
                        // Once a player does so, Sin Prodder deals damage equal to that card's converted mana cost to that player immediately
                        // and Sin Prodder's trigger has no further action.
                        break;
                    }
                }
                if (putInGraveyard) {
                    controller.moveCards(card, Zone.GRAVEYARD, source, game);
                } else {
                    controller.moveCards(card, Zone.HAND, source, game);
                }
            }
            return true;

        }
        return false;
    }
}
