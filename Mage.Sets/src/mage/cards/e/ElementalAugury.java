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
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author Plopman
 */
public class ElementalAugury extends CardImpl {

    public ElementalAugury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{B}{R}");

        // {3}: Look at the top three cards of target player's library, then put them back in any order.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ElementalAuguryEffect(), new ManaCostsImpl("3"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public ElementalAugury(final ElementalAugury card) {
        super(card);
    }

    @Override
    public ElementalAugury copy() {
        return new ElementalAugury(this);
    }
}

class ElementalAuguryEffect extends OneShotEffect {

    public ElementalAuguryEffect() {
        super(Outcome.DrawCard);
        this.staticText = "look at the top three cards of target player's library, then put them back in any order";
    }

    public ElementalAuguryEffect(final ElementalAuguryEffect effect) {
        super(effect);
    }

    @Override
    public ElementalAuguryEffect copy() {
        return new ElementalAuguryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer == null
                || controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(targetPlayer.getLibrary().getTopCards(game, 3));
        controller.lookAtCards(source, null, cards, game);
        controller.putCardsOnTopOfLibrary(cards, game, source, true);
        return true;
    }
}
