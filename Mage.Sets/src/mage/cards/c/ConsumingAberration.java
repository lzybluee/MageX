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
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public final class ConsumingAberration extends CardImpl {

    public ConsumingAberration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");

        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        //Consuming Aberration's power and toughness are each equal to the number of cards in your opponents' graveyards.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(new CardsInOpponentsGraveyardsCount(), Duration.EndOfGame)));
        //Whenever you cast a spell, each opponent reveals cards from the top of their library until he or she reveals a land card, then puts those cards into their graveyard.
        this.addAbility(new SpellCastControllerTriggeredAbility(new ConsumingAberrationEffect(), false));
    }

    public ConsumingAberration(final ConsumingAberration card) {
        super(card);
    }

    @Override
    public ConsumingAberration copy() {
        return new ConsumingAberration(this);
    }
}

class ConsumingAberrationEffect extends OneShotEffect {

    public ConsumingAberrationEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "each opponent reveals cards from the top of their library until he or she reveals a land card, then puts those cards into their graveyard";
    }

    public ConsumingAberrationEffect(final ConsumingAberrationEffect effect) {
        super(effect);
    }

    @Override
    public ConsumingAberrationEffect copy() {
        return new ConsumingAberrationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(opponentId);
            if (player == null) {
                continue;
            }
            Cards cards = new CardsImpl();
            for (Card card : player.getLibrary().getCards(game)) {
                if (card != null) {
                    cards.add(card);
                    if (card.isLand()) {
                        break;
                    }
                }
            }
            player.revealCards(source, cards, game);
            player.moveCards(cards, Zone.GRAVEYARD, source, game);
        }
        return true;
    }
}

class CardsInOpponentsGraveyardsCount implements DynamicValue {

    public CardsInOpponentsGraveyardsCount() {
        super();
    }

    public CardsInOpponentsGraveyardsCount(DynamicValue count) {
        super();
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int amount = 0;
        for (UUID playerUUID : game.getOpponents(sourceAbility.getControllerId())) {
            Player player = game.getPlayer(playerUUID);
            if (player != null) {
                amount += player.getGraveyard().size();
            }
        }
        return amount;
    }

    @Override
    public DynamicValue copy() {
        return new CardsInOpponentsGraveyardsCount(this);
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "cards in your opponents' graveyards";
    }
}
