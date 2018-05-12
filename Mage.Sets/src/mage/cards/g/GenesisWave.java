/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.cards.g;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class GenesisWave extends CardImpl {

    public GenesisWave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}{G}{G}");

        // Reveal the top X cards of your library. You may put any number of permanent cards with converted mana
        // cost X or less from among them onto the battlefield. Then put all cards revealed this way that weren't
        // put onto the battlefield into your graveyard.
        this.getSpellAbility().addEffect(new GenesisWaveEffect());
    }

    public GenesisWave(final GenesisWave card) {
        super(card);
    }

    @Override
    public GenesisWave copy() {
        return new GenesisWave(this);
    }

}

class GenesisWaveEffect extends OneShotEffect {

    public GenesisWaveEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "Reveal the top X cards of your library. You may put any number of permanent cards with converted mana cost X or less from among them onto the battlefield. Then put all cards revealed this way that weren't put onto the battlefield into your graveyard";
    }

    public GenesisWaveEffect(final GenesisWaveEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int xValue = source.getManaCostsToPay().getX();
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, xValue));
        if (!cards.isEmpty()) {
            controller.revealCards(source, cards, game);
            FilterCard filter = new FilterPermanentCard("cards with converted mana cost " + xValue + " or less to put onto the battlefield");
            filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, xValue + 1));
            TargetCard target1 = new TargetCard(0, Integer.MAX_VALUE, Zone.LIBRARY, filter);
            target1.setNotTarget(true);
            controller.choose(Outcome.PutCardInPlay, cards, target1, game);
            Cards toBattlefield = new CardsImpl(target1.getTargets());
            cards.removeAll(toBattlefield);
            controller.moveCards(toBattlefield.getCards(game), Zone.BATTLEFIELD, source, game, false, false, false, null);
            controller.moveCards(cards, Zone.GRAVEYARD, source, game);
        }
        return true;
    }

    @Override
    public GenesisWaveEffect copy() {
        return new GenesisWaveEffect(this);
    }

}
