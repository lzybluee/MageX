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
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.h.HomicidalBrute;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 * @author nantuko
 */
public class CivilizedScholar extends CardImpl {

    public CivilizedScholar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);

        this.transformable = true;
        this.secondSideCardClazz = HomicidalBrute.class;

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // {tap}: Draw a card, then discard a card. If a creature card is discarded this way, untap Civilized Scholar, then transform it.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CivilizedScholarEffect(), new TapSourceCost()));
        this.addAbility(new TransformAbility(), new HomicidalBruteWatcher());
    }

    public CivilizedScholar(final CivilizedScholar card) {
        super(card);
    }

    @Override
    public CivilizedScholar copy() {
        return new CivilizedScholar(this);
    }
}

class HomicidalBruteWatcher extends Watcher {

    public HomicidalBruteWatcher() {
        super(HomicidalBruteWatcher.class.getSimpleName(), WatcherScope.CARD);
    }

    public HomicidalBruteWatcher(final HomicidalBruteWatcher watcher) {
        super(watcher);
    }

    @Override
    public HomicidalBruteWatcher copy() {
        return new HomicidalBruteWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (condition == true) {
            return;
        }
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED && event.getSourceId().equals(sourceId)) {
            condition = true;
        }
    }
}

class CivilizedScholarEffect extends OneShotEffect {

    public CivilizedScholarEffect() {
        super(Outcome.DrawCard);
        staticText = "Draw a card, then discard a card. If a creature card is discarded this way, untap {this}, then transform it";
    }

    public CivilizedScholarEffect(final CivilizedScholarEffect effect) {
        super(effect);
    }

    @Override
    public CivilizedScholarEffect copy() {
        return new CivilizedScholarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.drawCards(1, game);
            Card card = player.discardOne(false, source, game);
            if (card != null && card.isCreature()) {
                Permanent permanent = game.getPermanent(source.getSourceId());
                if (permanent != null) {
                    permanent.untap(game);
                    permanent.transform(game);
                }
            }
            return true;
        }
        return false;
    }
}
