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

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 * http://www.wizards.com/magic/magazine/article.aspx?x=mtg/faq/rtr
 * Shrieking Affliction's ability will trigger only if an opponent begins his or
 * her upkeep with one or fewer cards in hand.
 * The ability will check the number of cards in that player's hand again when
 * it tries to resolve. If that player has two or more cards in hand at that time,
 * that player won't lose life.
 *
 * @author LevelX2
 */

public final class ShriekingAffliction extends CardImpl {

    static final String rule = "At the beginning of the upkeep of enchanted creature's controller, that player loses 2 life";

    public ShriekingAffliction (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{B}");


        // At the beginning of each opponent's upkeep, if that player has one or fewer cards in hand, he or she loses 3 life.
        this.addAbility(new ShriekingAfflictionTriggeredAbility());
    }

    public ShriekingAffliction (final ShriekingAffliction card) {
        super(card);
    }

    @Override
    public ShriekingAffliction copy() {
        return new ShriekingAffliction(this);
    }
}

class ShriekingAfflictionTriggeredAbility extends TriggeredAbilityImpl {

    public ShriekingAfflictionTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    public ShriekingAfflictionTriggeredAbility(final ShriekingAfflictionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ShriekingAfflictionTriggeredAbility copy() {
        return new ShriekingAfflictionTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(controllerId).contains(event.getPlayerId())) {
            Player player = game.getPlayer(event.getPlayerId());
            if (player != null && player.getHand().size() < 2) {
                this.getEffects().clear();
                ShriekingAfflictionTargetEffect effect = new ShriekingAfflictionTargetEffect();
                effect.setTargetPointer(new FixedTarget(player.getId()));
                this.addEffect(effect);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of each opponent's upkeep, if that player has one or fewer cards in hand, he or she loses 3 life.";
    }
}
class ShriekingAfflictionTargetEffect extends OneShotEffect {

    public ShriekingAfflictionTargetEffect() {
        super(Outcome.Damage);
        staticText = "he or she loses 3 life";
    }

    public ShriekingAfflictionTargetEffect(final ShriekingAfflictionTargetEffect effect) {
        super(effect);
    }

    @Override
    public ShriekingAfflictionTargetEffect copy() {
        return new ShriekingAfflictionTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null && player.getHand().size() < 2) {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (sourcePermanent != null) {
                game.informPlayers(sourcePermanent.getName() + ": " + player.getLogName() + " loses 3 life");
            }
            player.loseLife(3, game, false);
            return true;
        }
        return false;
    }

}
