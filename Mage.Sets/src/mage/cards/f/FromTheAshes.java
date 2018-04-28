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
package mage.cards.f;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public class FromTheAshes extends CardImpl {

    public FromTheAshes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Destroy all nonbasic lands. For each land destroyed this way, its controller may search their library for a basic land card and put it onto the battlefield. Then each player who searched their library this way shuffles it.
        this.getSpellAbility().addEffect(new FromTheAshesEffect());
    }

    public FromTheAshes(final FromTheAshes card) {
        super(card);
    }

    @Override
    public FromTheAshes copy() {
        return new FromTheAshes(this);
    }
}

class FromTheAshesEffect extends OneShotEffect {

    private static final FilterLandPermanent filter = FilterLandPermanent.nonbasicLands();

    public FromTheAshesEffect() {
        super(Outcome.Benefit);
        this.staticText = "Destroy all nonbasic lands. For each land destroyed this way, its controller may search their library for a basic land card and put it onto the battlefield. Then each player who searched their library this way shuffles it";
    }

    public FromTheAshesEffect(final FromTheAshesEffect effect) {
        super(effect);
    }

    @Override
    public FromTheAshesEffect copy() {
        return new FromTheAshesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Map<UUID, Integer> playerAmount = new HashMap<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int amount = 0;
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, playerId, game)) {
                        amount++;
                        permanent.destroy(source.getSourceId(), game, false);
                    }
                    playerAmount.put(playerId, amount);
                }
            }
            game.applyEffects();
            for (Map.Entry<UUID, Integer> entry : playerAmount.entrySet()) {
                Player player = game.getPlayer(entry.getKey());
                if (player != null && player.chooseUse(outcome, "Search your library for up to " + entry.getValue() + " basic land card(s) to put it onto the battlefield?", source, game)) {
                    TargetCardInLibrary target = new TargetCardInLibrary(0, entry.getValue(), StaticFilters.FILTER_BASIC_LAND_CARD);
                    if (player.searchLibrary(target, game)) {
                        if (!target.getTargets().isEmpty()) {
                            player.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, source, game);
                        }
                    }
                } else {
                    entry.setValue(0); // no search no shuffling
                }
            }
            game.applyEffects();
            for (Map.Entry<UUID, Integer> entry : playerAmount.entrySet()) {
                Player player = game.getPlayer(entry.getKey());
                if (player != null && entry.getValue() > 0) {
                    player.shuffleLibrary(source, game);
                }
            }
            return true;
        }
        return false;
    }
}
