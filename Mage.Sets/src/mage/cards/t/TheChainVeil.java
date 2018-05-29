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
package mage.cards.t;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public final class TheChainVeil extends CardImpl {

    public TheChainVeil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");
        addSuperType(SuperType.LEGENDARY);

        // At the beginning of your end step, if you didn't activate a loyalty ability of a planeswalker this turn, you lose 2 life.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD, new LoseLifeSourceControllerEffect(2), TargetController.YOU, TheChainVeilCondition.instance, false), new ActivatedLoyaltyAbilityWatcher());

        // {4}, {T}: For each planeswalker you control, you may activate one of its loyalty abilities once this turn as though none of its loyalty abilities had been activated this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new TheChainVeilIncreaseLoyaltyUseEffect(),
                new ManaCostsImpl("{4}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    public TheChainVeil(final TheChainVeil card) {
        super(card);
    }

    @Override
    public TheChainVeil copy() {
        return new TheChainVeil(this);
    }
}

class ActivatedLoyaltyAbilityWatcher extends Watcher {

    private final Set<UUID> playerIds = new HashSet<>();

    public ActivatedLoyaltyAbilityWatcher() {
        super(ActivatedLoyaltyAbilityWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public ActivatedLoyaltyAbilityWatcher(final ActivatedLoyaltyAbilityWatcher watcher) {
        super(watcher);
        playerIds.addAll(watcher.playerIds);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ACTIVATED_ABILITY) {
            StackObject stackObject = game.getStack().getStackObject(event.getTargetId());
            if (stackObject != null
                    && stackObject.getStackAbility() != null
                    && stackObject.getStackAbility() instanceof LoyaltyAbility) {
                playerIds.add(stackObject.getControllerId());
            }
        }
    }

    @Override
    public void reset() {
        playerIds.clear();
    }

    @Override
    public ActivatedLoyaltyAbilityWatcher copy() {
        return new ActivatedLoyaltyAbilityWatcher(this);
    }

    public boolean activatedLoyaltyAbility(UUID playerId) {
        return playerIds.contains(playerId);
    }
}

class TheChainVeilIncreaseLoyaltyUseEffect extends ContinuousEffectImpl {

    public TheChainVeilIncreaseLoyaltyUseEffect() {
        super(Duration.EndOfTurn, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "For each planeswalker you control, you may activate one of its loyalty abilities once this turn as though none of its loyalty abilities had been activated this turn";
    }

    public TheChainVeilIncreaseLoyaltyUseEffect(final TheChainVeilIncreaseLoyaltyUseEffect effect) {
        super(effect);
    }

    @Override
    public TheChainVeilIncreaseLoyaltyUseEffect copy() {
        return new TheChainVeilIncreaseLoyaltyUseEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.setLoyaltyUsePerTurn(controller.getLoyaltyUsePerTurn() + 1);
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}

enum TheChainVeilCondition implements Condition {

    instance;



    @Override
    public boolean apply(Game game, Ability source) {
        ActivatedLoyaltyAbilityWatcher watcher = (ActivatedLoyaltyAbilityWatcher) game.getState().getWatchers().get(ActivatedLoyaltyAbilityWatcher.class.getSimpleName());
        if (watcher != null) {
            if (!watcher.activatedLoyaltyAbility(source.getControllerId())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "if you didn't activate a loyalty ability of a planeswalker this turn";
    }

}
