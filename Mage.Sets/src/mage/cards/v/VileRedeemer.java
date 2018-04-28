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
package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.EldraziScionToken;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author LevelX2
 */
public class VileRedeemer extends CardImpl {

    public VileRedeemer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));
        // Flash
        this.addAbility(FlashAbility.getInstance());
        // When you cast Vile Redeemer, you may pay {C}. If you do create a 1/1 colorless Eldrazi Scion creature token for each nontoken creature that died under your control this turn. They have "Sacrifice this creature: Add {C}."
        this.addAbility(
                new CastSourceTriggeredAbility(new DoIfCostPaid(new VileRedeemerEffect(), new ManaCostsImpl("{C}"), "Pay {C} to create 1/1 colorless Eldrazi Scion creature tokens?"), false),
                new VileRedeemerNonTokenCreaturesDiedWatcher());
    }

    public VileRedeemer(final VileRedeemer card) {
        super(card);
    }

    @Override
    public VileRedeemer copy() {
        return new VileRedeemer(this);
    }
}

class VileRedeemerEffect extends OneShotEffect {

    public VileRedeemerEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create a 1/1 colorless Eldrazi Scion creature token for each nontoken creature that died under your control this turn. They have \"Sacrifice this creature: Add {C}";
    }

    public VileRedeemerEffect(final VileRedeemerEffect effect) {
        super(effect);
    }

    @Override
    public VileRedeemerEffect copy() {
        return new VileRedeemerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            VileRedeemerNonTokenCreaturesDiedWatcher watcher = (VileRedeemerNonTokenCreaturesDiedWatcher) game.getState().getWatchers().get(VileRedeemerNonTokenCreaturesDiedWatcher.class.getSimpleName());
            if (watcher != null) {
                int amount = watcher.getAmountOfNontokenCreatureDiedThisTurn(controller.getId());
                if (amount > 0) {
                    return new CreateTokenEffect(new EldraziScionToken(), amount).apply(game, source);
                }
            }
            return true;
        }
        return false;
    }
}

class VileRedeemerNonTokenCreaturesDiedWatcher extends Watcher {

    private final Map<UUID, Integer> amountOfCreaturesThatDied = new HashMap<>();

    public VileRedeemerNonTokenCreaturesDiedWatcher() {
        super(VileRedeemerNonTokenCreaturesDiedWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public VileRedeemerNonTokenCreaturesDiedWatcher(final VileRedeemerNonTokenCreaturesDiedWatcher watcher) {
        super(watcher);
        this.amountOfCreaturesThatDied.putAll(watcher.amountOfCreaturesThatDied);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.isDiesEvent() && zEvent.getTarget() != null
                    && zEvent.getTarget().isCreature()
                    && !(zEvent.getTarget() instanceof PermanentToken)) {
                int count = getAmountOfNontokenCreatureDiedThisTurn(zEvent.getTargetId());
                amountOfCreaturesThatDied.put(zEvent.getTarget().getControllerId(), ++count);
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        amountOfCreaturesThatDied.clear();
    }

    public int getAmountOfNontokenCreatureDiedThisTurn(UUID playerId) {
        return amountOfCreaturesThatDied.getOrDefault(playerId, 0);
    }

    @Override
    public VileRedeemerNonTokenCreaturesDiedWatcher copy() {
        return new VileRedeemerNonTokenCreaturesDiedWatcher(this);
    }

}
