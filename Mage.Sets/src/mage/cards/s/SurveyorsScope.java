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

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class SurveyorsScope extends CardImpl {

    public SurveyorsScope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {tap}, Exile Surveyor's Scope: Search your library for up to X basic land cards, where X is the number of players who control at least two more lands than you. Put those cards onto the battlefield, then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SurveyorsScopeEffect(), new TapSourceCost());
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    public SurveyorsScope(final SurveyorsScope card) {
        super(card);
    }

    @Override
    public SurveyorsScope copy() {
        return new SurveyorsScope(this);
    }
}

class SurveyorsScopeEffect extends OneShotEffect {

    public SurveyorsScopeEffect() {
        super(Outcome.PutLandInPlay);
        this.staticText = "Search your library for up to X basic land cards, where X is the number of players who control at least two more lands than you. Put those cards onto the battlefield, then shuffle your library";
    }

    public SurveyorsScopeEffect(final SurveyorsScopeEffect effect) {
        super(effect);
    }

    @Override
    public SurveyorsScopeEffect copy() {
        return new SurveyorsScopeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int numberOfLands = 0;
            int ownLands = game.getBattlefield().countAll(new FilterLandPermanent(), controller.getId(), game);
            for (UUID playerId: game.getState().getPlayersInRange(controller.getId(), game)) {
                if (!playerId.equals(controller.getId())) {
                    if (game.getBattlefield().countAll(new FilterLandPermanent(), playerId, game) > ownLands + 1) {
                        numberOfLands++;
                    }
                }
            }
            game.informPlayers(new StringBuilder("Surveyor's Scope: X = ").append(numberOfLands).toString());
            // 10/17/2013 	If no players control at least two more lands than you when the ability resolves, you’ll still search and shuffle your library.
            return new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, numberOfLands, StaticFilters.FILTER_BASIC_LAND_CARD)).apply(game, source);
        }
        return false;
    }
}
