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

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class CurseOfInertia extends CardImpl {

    public CurseOfInertia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");
        this.subtype.add(SubType.AURA, SubType.CURSE);


        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Whenever a player attacks enchanted player with one or more creatures, that attacking player may tap or untap target permanent of their choice.
        this.addAbility(new CurseOfInertiaTriggeredAbility());

    }

    public CurseOfInertia(final CurseOfInertia card) {
        super(card);
    }

    @Override
    public CurseOfInertia copy() {
        return new CurseOfInertia(this);
    }
}

class CurseOfInertiaTriggeredAbility extends TriggeredAbilityImpl {

    public CurseOfInertiaTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CurseOfInertiaTapOrUntapTargetEffect(), false);
    }
    
    public CurseOfInertiaTriggeredAbility(final CurseOfInertiaTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.getSourceId());
        if (enchantment != null
                && enchantment.getAttachedTo() != null
                && game.getCombat().getPlayerDefenders(game).contains(enchantment.getAttachedTo())) {
            TargetPermanent target = new TargetPermanent();
            target.setTargetController(game.getCombat().getAttackingPlayerId());
            addTarget(target);
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player attacks enchanted player with one or more creatures, that attacking player may tap or untap target permanent of their choice.";
    }

    @Override
    public CurseOfInertiaTriggeredAbility copy() {
        return new CurseOfInertiaTriggeredAbility(this);
    }

}

class CurseOfInertiaTapOrUntapTargetEffect extends OneShotEffect {
    public CurseOfInertiaTapOrUntapTargetEffect() {
        super(Outcome.Tap);
        staticText = "tap or untap target permanent of their choice";
    }

    public CurseOfInertiaTapOrUntapTargetEffect(final CurseOfInertiaTapOrUntapTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getTargets().get(0).getTargetController());
        if (player != null) {
            Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetPermanent != null) {
                if (targetPermanent.isTapped()) {
                    if (player.chooseUse(Outcome.Untap, "Untap that permanent?", source, game)) {
                        targetPermanent.untap(game);
                    }
                } else {
                    if (player.chooseUse(Outcome.Tap, "Tap that permanent?", source, game)) {
                        targetPermanent.tap(game);
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public CurseOfInertiaTapOrUntapTargetEffect copy() {
        return new CurseOfInertiaTapOrUntapTargetEffect(this);
    }

}
