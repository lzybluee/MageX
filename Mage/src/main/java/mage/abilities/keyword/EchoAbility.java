/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.keyword;

import java.util.Locale;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Backfir3
 */
public class EchoAbility extends TriggeredAbilityImpl {

    protected UUID lastController;
    protected boolean echoPaid;
    protected Costs<Cost> echoCosts = new CostsImpl<>();
    private boolean manaEcho = true;
    private DynamicValue amount;
    private String rule;

    public EchoAbility(String manaString) {
        super(Zone.BATTLEFIELD, new EchoEffect(new ManaCostsImpl(manaString)), false);
        this.echoPaid = false;
        this.echoCosts.add(new ManaCostsImpl(manaString));
        this.lastController = null;
        this.rule = null;
    }

    public EchoAbility(DynamicValue amount, String rule) {
        super(Zone.BATTLEFIELD, new EchoEffect(amount), false);
        this.amount = amount;
        this.echoPaid = false;
        this.echoCosts.add(costs);
        this.lastController = null;
        this.manaEcho = true;
        this.rule = rule;
    }

    public EchoAbility(Cost echoCost) {
        super(Zone.BATTLEFIELD, new EchoEffect(echoCost), false);
        this.echoPaid = false;
        this.echoCosts.add(echoCost);
        this.manaEcho = false;
        this.lastController = null;
        this.rule = null;
    }

    public EchoAbility(final EchoAbility ability) {
        super(ability);
        this.echoPaid = ability.echoPaid;
        this.echoCosts = ability.echoCosts.copy();
        this.manaEcho = ability.manaEcho;
        this.lastController = ability.lastController;
        this.amount = ability.amount;
        this.rule = ability.rule;
    }

    @Override
    public EchoAbility copy() {
        return new EchoAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                || event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // reset the echo paid state back, if creature enteres the battlefield
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                && event.getTargetId().equals(this.getSourceId())) {

            this.echoPaid = false;
        }
        if (event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE) {
            if (lastController != null) {
                if (!lastController.equals(this.controllerId)) {
                    this.echoPaid = false;
                }
            }
            // remember the last controller
            lastController = this.getControllerId();
            // if echo not paid yet, controller has to pay
            if (event.getPlayerId().equals(this.controllerId)
                    && lastController.equals(this.controllerId)
                    && !this.echoPaid) {
                this.echoPaid = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder();
        if (rule != null) {
            sb.append(rule);
        } else {
            sb = new StringBuilder("Echo");
            if (manaEcho) {
                sb.append(' ');
            } else {
                sb.append("&mdash;");
            }
            if (echoCosts != null) {
                sb.append(echoCosts.getText());
            }
        }
        sb.append(" <i>(At the beginning of your upkeep, if this came under your control since the beginning of your last upkeep, sacrifice it unless you pay its echo cost.)</i>");
        return sb.toString();
    }
}

class EchoEffect extends OneShotEffect {

    protected Cost cost;
    protected DynamicValue amount;

    public EchoEffect(Cost costs) {
        super(Outcome.Sacrifice);
        this.cost = costs;
        this.amount = null;
    }

    public EchoEffect(DynamicValue amount) {
        super(Outcome.Sacrifice);
        this.amount = amount;
        this.cost = null;
    }

    public EchoEffect(final EchoEffect effect) {
        super(effect);
        this.cost = effect.cost;
        this.amount = effect.amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (amount != null) {
            cost = new ManaCostsImpl(Integer.toString(amount.calculate(game, source, this)));
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null
                && source.getSourceObjectIfItStillExists(game) != null) {
            if (controller.chooseUse(Outcome.Benefit, "Pay " + cost.getText() + '?', source, game)) {
                cost.clearPaid();
                if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false, null)) {
                    game.fireEvent(GameEvent.getEvent(GameEvent.EventType.ECHO_PAID, source.getSourceId(), source.getSourceId(), source.getControllerId()));
                    return true;
                }
            }
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                permanent.sacrifice(source.getSourceId(), game);
            }
            return true;
        }
        return false;
    }

    @Override
    public EchoEffect copy() {
        return new EchoEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder("sacrifice {this} unless you ");
        String costText = cost.getText();
        if (costText.toLowerCase(Locale.ENGLISH).startsWith("discard")) {
            sb.append(costText.substring(0, 1).toLowerCase(Locale.ENGLISH));
            sb.append(costText.substring(1));
        } else {
            sb.append("pay ").append(costText);
        }

        return sb.toString();

    }
}
