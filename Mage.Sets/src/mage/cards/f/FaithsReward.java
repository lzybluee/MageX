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

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.watchers.Watcher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class FaithsReward extends CardImpl {

    public FaithsReward(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{W}");


        // Return to the battlefield all permanent cards in your graveyard that were put there from the battlefield this turn.
        this.getSpellAbility().addEffect(new FaithsRewardEffect());
        this.getSpellAbility().addWatcher(new FaithsRewardWatcher());
    }

    public FaithsReward(final FaithsReward card) {
        super(card);
    }

    @Override
    public FaithsReward copy() {
        return new FaithsReward(this);
    }
}

class FaithsRewardEffect extends OneShotEffect {

    FaithsRewardEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "Return to the battlefield all permanent cards in your graveyard that were put there from the battlefield this turn";
    }

    FaithsRewardEffect(final FaithsRewardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FaithsRewardWatcher watcher = (FaithsRewardWatcher) game.getState().getWatchers().get(FaithsRewardWatcher.class.getSimpleName());
        if (watcher != null) {
            for (UUID id : watcher.cards) {
                Card c = game.getCard(id);
                if (c != null && c.getOwnerId().equals(source.getControllerId()) && game.getState().getZone(id) == Zone.GRAVEYARD) {
                    c.moveToZone(Zone.BATTLEFIELD, source.getSourceId(), game, false);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public FaithsRewardEffect copy() {
        return new FaithsRewardEffect(this);
    }
}

class FaithsRewardWatcher extends Watcher {
    List<UUID> cards = new ArrayList<>();

    public FaithsRewardWatcher() {
        super(FaithsRewardWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public FaithsRewardWatcher(final FaithsRewardWatcher watcher) {
        super(watcher);
        this.cards.addAll(watcher.cards);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent)event).isDiesEvent()) {
            cards.add(event.getTargetId());
        }
    }

    @Override
    public FaithsRewardWatcher copy() {
        return new FaithsRewardWatcher(this);
    }

    @Override
    public void reset() {
        super.reset();
        cards.clear();
    }
}
