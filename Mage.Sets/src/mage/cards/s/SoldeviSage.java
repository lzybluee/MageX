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
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Quercitron
 */
public class SoldeviSage extends CardImpl {

    public SoldeviSage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}, Sacrifice two lands: Draw three cards, then discard one of them.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SoldeviSageEffect(), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(2, 2, new FilterControlledLandPermanent("two lands"), true)));
        this.addAbility(ability);
    }

    public SoldeviSage(final SoldeviSage card) {
        super(card);
    }

    @Override
    public SoldeviSage copy() {
        return new SoldeviSage(this);
    }
}

class SoldeviSageEffect extends OneShotEffect {

    public SoldeviSageEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Draw three cards, then discard one of them";
    }

    public SoldeviSageEffect(final SoldeviSageEffect effect) {
        super(effect);
    }

    @Override
    public SoldeviSageEffect copy() {
        return new SoldeviSageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Cards initialHand = player.getHand().copy();
            player.drawCards(3, game);
            Cards drawnCards = new CardsImpl();
            for (UUID cardId : player.getHand()) {
                if (!initialHand.contains(cardId)) {
                    drawnCards.add(cardId);
                }
            }

            if (!drawnCards.isEmpty()) {
                TargetCard cardToDiscard = new TargetCard(Zone.HAND, new FilterCard("card to discard"));
                cardToDiscard.setNotTarget(true);
                if (player.choose(Outcome.Discard, drawnCards, cardToDiscard, game)) {
                    Card card = player.getHand().get(cardToDiscard.getFirstTarget(), game);
                    if (card != null) {
                        return player.discard(card, source, game);
                    }
                }
            }

            return true;
        }
        return false;
    }

}
