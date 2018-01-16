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
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class AngrathTheFlameChained extends CardImpl {

    public AngrathTheFlameChained(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGRATH);
        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(4));

        // +1: Each opponent discards a card and loses 2 life.
        LoyaltyAbility ability = new LoyaltyAbility(new DiscardEachPlayerEffect(TargetController.OPPONENT), 1);
        ability.addEffect(new LoseLifeOpponentsEffect(2).setText("and loses 2 life"));
        this.addAbility(ability);

        // -3: Gain control of target creature until end of turn. Untap it. It gains haste until end of turn. Sacrifice it at the beginning of the next end step if it has converted mana cost 3 or less.
        ability = new LoyaltyAbility(new GainControlTargetEffect(Duration.EndOfTurn), -3);
        ability.addEffect(new UntapTargetEffect().setText("untap it"));
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn).setText("It gains haste until end of turn"));
        ability.addEffect(new AngrathTheFlameCreateDelayedTriggerEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // -8: Each opponent loses life equal to the number of cards in his or her graveyard.
        this.addAbility(new LoyaltyAbility(new AngrathTheFlameUltimateEffect(), -8));

    }

    public AngrathTheFlameChained(final AngrathTheFlameChained card) {
        super(card);
    }

    @Override
    public AngrathTheFlameChained copy() {
        return new AngrathTheFlameChained(this);
    }
}

class AngrathTheFlameUltimateEffect extends OneShotEffect {

    public AngrathTheFlameUltimateEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each opponent loses life equal to the number of cards in his or her graveyard";
    }

    public AngrathTheFlameUltimateEffect(final AngrathTheFlameUltimateEffect effect) {
        super(effect);
    }

    @Override
    public AngrathTheFlameUltimateEffect copy() {
        return new AngrathTheFlameUltimateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(opponentId);
            if (player != null && !player.getGraveyard().isEmpty()) {
                player.loseLife(player.getGraveyard().size(), game, false);
            }
        }
        return true;
    }
}

class AngrathTheFlameCreateDelayedTriggerEffect extends OneShotEffect {

    public AngrathTheFlameCreateDelayedTriggerEffect() {
        super(Outcome.Sacrifice);
        staticText = "Sacrifice it at the beginning of the next end step if it has converted mana cost 3 or less";
    }

    public AngrathTheFlameCreateDelayedTriggerEffect(final AngrathTheFlameCreateDelayedTriggerEffect effect) {
        super(effect);
    }

    @Override
    public AngrathTheFlameCreateDelayedTriggerEffect copy() {
        return new AngrathTheFlameCreateDelayedTriggerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            SacrificeTargetEffect sacrificeEffect = new SacrificeTargetEffect("sacrifice this", source.getControllerId());
            sacrificeEffect.setTargetPointer(new FixedTarget(permanent, game));
            DelayedTriggeredAbility delayedAbility = new AngrathTheFlameChainedDelayedTriggeredAbility(sacrificeEffect);
            game.addDelayedTriggeredAbility(delayedAbility, source);
            return true;
        }
        return false;
    }
}

class AngrathTheFlameChainedDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public AngrathTheFlameChainedDelayedTriggeredAbility(Effect effect) {
        super(effect, Duration.Custom);
    }

    public AngrathTheFlameChainedDelayedTriggeredAbility(final AngrathTheFlameChainedDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.END_TURN_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(((FixedTarget) getEffects().get(0).getTargetPointer()).getTarget());
        return permanent != null
                && permanent.getZoneChangeCounter(game) == ((FixedTarget) getEffects().get(0).getTargetPointer()).getZoneChangeCounter()
                && permanent.getConvertedManaCost() <= 3;
    }

    @Override
    public AngrathTheFlameChainedDelayedTriggeredAbility copy() {
        return new AngrathTheFlameChainedDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Sacrifice it at the beginning of the next end step if it has converted mana cost 3 or less.";
    }
}
