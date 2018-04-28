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
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileGraveyardAllPlayersEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author jonubuu
 */
public class RelicOfProgenitus extends CardImpl {

    public RelicOfProgenitus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {tap}: Target player exiles a card from their graveyard.
        Ability firstAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RelicOfProgenitusEffect(), new TapSourceCost());
        firstAbility.addTarget(new TargetPlayer());
        this.addAbility(firstAbility);
        // {1}, Exile Relic of Progenitus: Exile all cards from all graveyards. Draw a card.
        Ability secondAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileGraveyardAllPlayersEffect(), new GenericManaCost(1));
        secondAbility.addCost(new ExileSourceCost());
        secondAbility.addEffect(new DrawCardSourceControllerEffect(1));
        this.addAbility(secondAbility);
    }

    public RelicOfProgenitus(final RelicOfProgenitus card) {
        super(card);
    }

    @Override
    public RelicOfProgenitus copy() {
        return new RelicOfProgenitus(this);
    }
}

class RelicOfProgenitusEffect extends OneShotEffect {

    public RelicOfProgenitusEffect() {
        super(Outcome.Exile);
        this.staticText = "Target player exiles a card from their graveyard";
    }

    public RelicOfProgenitusEffect(final RelicOfProgenitusEffect effect) {
        super(effect);
    }

    @Override
    public RelicOfProgenitusEffect copy() {
        return new RelicOfProgenitusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer != null) {
            FilterCard filter = new FilterCard("card from your graveyard");
            filter.add(new OwnerIdPredicate(targetPlayer.getId()));
            TargetCardInGraveyard target = new TargetCardInGraveyard(filter);
            if (targetPlayer.chooseTarget(Outcome.Exile, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    targetPlayer.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.GRAVEYARD, true);
                }
                return true;
            }
        }
        return false;
    }
}
