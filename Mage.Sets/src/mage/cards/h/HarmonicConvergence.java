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
package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.*;

/**
 *
 * @author North
 */
public class HarmonicConvergence extends CardImpl {

    public HarmonicConvergence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}");


        // Put all enchantments on top of their owners' libraries.
        this.getSpellAbility().addEffect(new HarmonicConvergenceEffect());
    }

    public HarmonicConvergence(final HarmonicConvergence card) {
        super(card);
    }

    @Override
    public HarmonicConvergence copy() {
        return new HarmonicConvergence(this);
    }
}

class HarmonicConvergenceEffect extends OneShotEffect {

    public HarmonicConvergenceEffect() {
        super(Outcome.Neutral);
        this.staticText = "Put all enchantments on top of their owners' libraries";
    }

    public HarmonicConvergenceEffect(final HarmonicConvergenceEffect effect) {
        super(effect);
    }

    @Override
    public HarmonicConvergenceEffect copy() {
        return new HarmonicConvergenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> enchantments = game.getBattlefield().getActivePermanents(StaticFilters.FILTER_ENCHANTMENT_PERMANENT,
                source.getControllerId(),
                source.getSourceId(),
                game);

        Map<UUID, List<Permanent>> moveList = new HashMap<>();
        for (Permanent permanent : enchantments) {
            List<Permanent> list = moveList.computeIfAbsent(permanent.getControllerId(), k -> new ArrayList<>());
            list.add(permanent);
        }

        TargetCard target = new TargetCard(Zone.BATTLEFIELD, new FilterCard("card to put on top of your library"));
        for (UUID playerId : moveList.keySet()) {
            Player player = game.getPlayer(playerId);
            List<Permanent> list = moveList.get(playerId);
            if (player == null) {
                continue;
            }

            CardsImpl cards = new CardsImpl();
            for (Permanent permanent : list) {
                cards.add(permanent);
            }
            while (player.canRespond() && cards.size() > 1) {
                player.choose(Outcome.Neutral, cards, target, game);
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    cards.remove(permanent);
                    permanent.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                }
                target.clearChosen();
            }
            if (cards.size() == 1) {
                Permanent permanent = game.getPermanent(cards.iterator().next());
                permanent.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
            }
        }

        return true;
    }
}
