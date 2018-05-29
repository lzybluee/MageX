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

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J (significantly based on wetterlicht)
 */
public final class FracturedLoyalty extends CardImpl {

    public FracturedLoyalty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Whenever enchanted creature becomes the target of a spell or ability, that spell or ability's controller gains control of that creature.
        this.addAbility(new FracturedLoyaltyTriggeredAbility());
    }

    public FracturedLoyalty(final FracturedLoyalty card) {
        super(card);
    }

    @Override
    public FracturedLoyalty copy() {
        return new FracturedLoyalty(this);
    }

    private static class FracturedLoyaltyEffect extends OneShotEffect {

        public FracturedLoyaltyEffect() {
            super(Outcome.GainControl);
            this.staticText = "that spell or ability's controller gains control of that creature";
        }

        private FracturedLoyaltyEffect(FracturedLoyaltyEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent enchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (enchantment != null) {
                Permanent enchantedCreature = game.getPermanent(enchantment.getAttachedTo());
                if (enchantedCreature != null) {
                    Player controller = game.getPlayer(enchantedCreature.getControllerId());
                    if (enchantment.getAttachedTo() != null) {
                        if (controller != null && !enchantedCreature.getControllerId().equals(this.getTargetPointer().getFirst(game, source))) {
                            ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, this.getTargetPointer().getFirst(game, source));
                            effect.setTargetPointer(new FixedTarget(enchantment.getAttachedTo()));
                            game.addEffect(effect, source);
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        @Override
        public Effect copy() {
            return new FracturedLoyaltyEffect(this);
        }

    }

    class FracturedLoyaltyTriggeredAbility extends TriggeredAbilityImpl {

        public FracturedLoyaltyTriggeredAbility() {
            super(Zone.BATTLEFIELD, new FracturedLoyaltyEffect(), false);
        }

        public FracturedLoyaltyTriggeredAbility(final FracturedLoyaltyTriggeredAbility ability) {
            super(ability);
        }

        @Override
        public FracturedLoyaltyTriggeredAbility copy() {
            return new FracturedLoyaltyTriggeredAbility(this);
        }

        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            return event.getType() == EventType.TARGETED;
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            Permanent enchantment = game.getPermanentOrLKIBattlefield(this.getSourceId());
            if (enchantment != null && enchantment.getAttachedTo() != null) {
                Permanent enchantedCreature = game.getPermanent(enchantment.getAttachedTo());
                if (enchantedCreature != null && event.getTargetId().equals(enchantment.getAttachedTo())) {
                    getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
                    return true;
                }
            }
            return false;
        }

        @Override
        public String getRule() {
            return "Whenever enchanted creature becomes the target of a spell or ability, that spell or ability's controller gains control of that creature.";
        }
    }
}
