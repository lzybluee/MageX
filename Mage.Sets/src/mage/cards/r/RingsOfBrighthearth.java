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
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class RingsOfBrighthearth extends CardImpl {

    public RingsOfBrighthearth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Whenever you activate an ability, if it isn't a mana ability, you may pay {2}. If you do, copy that ability. You may choose new targets for the copy.
        this.addAbility(new RingsOfBrighthearthTriggeredAbility());
    }

    public RingsOfBrighthearth(final RingsOfBrighthearth card) {
        super(card);
    }

    @Override
    public RingsOfBrighthearth copy() {
        return new RingsOfBrighthearth(this);
    }
}

class RingsOfBrighthearthTriggeredAbility extends TriggeredAbilityImpl {

    RingsOfBrighthearthTriggeredAbility() {
        super(Zone.BATTLEFIELD, new RingsOfBrighthearthEffect(), false);
    }

    RingsOfBrighthearthTriggeredAbility(final RingsOfBrighthearthTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RingsOfBrighthearthTriggeredAbility copy() {
        return new RingsOfBrighthearthTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(getControllerId())) {
            StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
            if (stackAbility != null && !(stackAbility.getStackAbility() instanceof ActivatedManaAbilityImpl)) {
                Effect effect = this.getEffects().get(0);
                effect.setValue("stackAbility", stackAbility);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you activate an ability, if it isn't a mana ability, you may pay {2}. If you do, copy that ability. You may choose new targets for the copy.";
    }
}

class RingsOfBrighthearthEffect extends OneShotEffect {

    RingsOfBrighthearthEffect() {
        super(Outcome.Benefit);
        this.staticText = ", you may pay {2}. If you do, copy that ability. You may choose new targets for the copy.";
    }

    RingsOfBrighthearthEffect(final RingsOfBrighthearthEffect effect) {
        super(effect);
    }

    @Override
    public RingsOfBrighthearthEffect copy() {
        return new RingsOfBrighthearthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ManaCostsImpl cost = new ManaCostsImpl("{2}");
        if (player != null) {
            if (cost.canPay(source, source.getSourceId(), player.getId(), game)
                    && player.chooseUse(Outcome.Benefit, "Pay " + cost.getText() + "? If you do, copy that ability. You may choose new targets for the copy.", source, game)) {
                if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false, null)) {
                    StackAbility ability = (StackAbility) getValue("stackAbility");
                    Player controller = game.getPlayer(source.getControllerId());
                    Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
                    if (ability != null && controller != null && sourcePermanent != null) {
                        ability.createCopyOnStack(game, source, source.getControllerId(), true);
                        game.informPlayers(sourcePermanent.getIdName() + ": " + controller.getLogName() + " copied activated ability");
                        return true;
                    }
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
