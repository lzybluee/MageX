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
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class EntersBattlefieldTriggeredAbility extends TriggeredAbilityImpl {

    protected String rulePrefix;
    protected boolean noRule;

    public EntersBattlefieldTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public EntersBattlefieldTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, null);
    }

    public EntersBattlefieldTriggeredAbility(Effect effect, boolean optional, boolean noRule) {
        this(effect, optional, null);
        this.noRule = noRule;
    }

    public EntersBattlefieldTriggeredAbility(Effect effect, boolean optional, String rulePrefix) {
        super(Zone.ALL, effect, optional); // Zone.All because a creature with trigger can be put into play and be sacrificed during the resolution of an effect (discard Obstinate Baloth with Smallpox)
        this.rulePrefix = rulePrefix;
    }

    public EntersBattlefieldTriggeredAbility(final EntersBattlefieldTriggeredAbility ability) {
        super(ability);
        this.rulePrefix = ability.rulePrefix;
        this.noRule = ability.noRule;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(getSourceId());
    }

    @Override
    public String getRule() {
        if (noRule) {
            return super.getRule();
        }
        return (rulePrefix != null ? rulePrefix : "") + "When {this} enters the battlefield, " + super.getRule();
    }

    @Override
    public EntersBattlefieldTriggeredAbility copy() {
        return new EntersBattlefieldTriggeredAbility(this);
    }
}
