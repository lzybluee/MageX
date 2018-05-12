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
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ConundrumSphinx extends CardImpl {

    public ConundrumSphinx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.subtype.add(SubType.SPHINX);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        //Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Conundrum Sphinx attacks, each player names a card. Then each player reveals the top card of their library.
        // If the card a player revealed is the card he or she named, that player puts it into their hand.
        // If it's not, that player puts it on the bottom of their library.
        this.addAbility(new AttacksTriggeredAbility(new ConundrumSphinxEffect(), false));
    }

    public ConundrumSphinx(final ConundrumSphinx card) {
        super(card);
    }

    @Override
    public ConundrumSphinx copy() {
        return new ConundrumSphinx(this);
    }

}

class ConundrumSphinxEffect extends OneShotEffect {

    public ConundrumSphinxEffect() {
        super(Outcome.DrawCard);
        staticText = "each player names a card. Then each player reveals the top card of their library. If the card a player revealed is the card he or she named, that player puts it into their hand. If it's not, that player puts it on the bottom of their library";
    }

    public ConundrumSphinxEffect(final ConundrumSphinxEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && sourceObject != null) {
            Choice cardChoice = new ChoiceImpl();
            cardChoice.setChoices(CardRepository.instance.getNames());
            Players:
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    if (player.getLibrary().hasCards()) {
                        cardChoice.clearChoice();
                        cardChoice.setMessage("Name a card");
                        if (!player.choose(Outcome.DrawCard, cardChoice, game) && player.canRespond()) {
                            continue Players;
                        }
                        String cardName = cardChoice.getChoice();
                        game.informPlayers(sourceObject.getLogName() + ", player: " + player.getLogName() + ", named: [" + cardName + ']');
                        Card card = player.getLibrary().getFromTop(game);
                        if (card != null) {
                            Cards cards = new CardsImpl(card);
                            player.revealCards(source, player.getName(), cards, game);
                            if (card.getName().equals(cardName)) {
                                player.moveCards(cards, Zone.HAND, source, game);
                            } else {
                                player.putCardsOnBottomOfLibrary(cards, game, source, false);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public ConundrumSphinxEffect copy() {
        return new ConundrumSphinxEffect(this);
    }

}
