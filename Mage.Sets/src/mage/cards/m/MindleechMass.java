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
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author jeffwadsworth
 */
public final class MindleechMass extends CardImpl {

    public MindleechMass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{B}{B}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Mindleech Mass deals combat damage to a player, you may look at that player's hand. If you do, you may cast a nonland card in it without paying that card's mana cost.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new MindleechMassEffect(), true, true));
    }

    public MindleechMass(final MindleechMass card) {
        super(card);
    }

    @Override
    public MindleechMass copy() {
        return new MindleechMass(this);
    }
}

class MindleechMassEffect extends OneShotEffect {

    public MindleechMassEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "you may look at that player's hand. If you do, you may cast a nonland card in it without paying that card's mana cost";
    }

    public MindleechMassEffect(final MindleechMassEffect effect) {
        super(effect);
    }

    @Override
    public MindleechMassEffect copy() {
        return new MindleechMassEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (opponent != null && controller != null) {
            Cards cardsInHand = new CardsImpl();
            cardsInHand.addAll(opponent.getHand());
            opponent.revealCards("Opponents hand", cardsInHand, game);
            if (!cardsInHand.isEmpty()
                    && !cardsInHand.getCards(new FilterNonlandCard(), game).isEmpty()) {
                TargetCard target = new TargetCard(1, Zone.HAND, new FilterNonlandCard());
                if (controller.chooseTarget(Outcome.PlayForFree, cardsInHand, target, source, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        controller.cast(card.getSpellAbility(), game, true, new MageObjectReference(source.getSourceObject(game), game));
                    }
                }
            }
            return true;
        }
        return false;
    }
}
