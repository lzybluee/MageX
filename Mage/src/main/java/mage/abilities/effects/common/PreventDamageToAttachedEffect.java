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
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.AttachmentType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class PreventDamageToAttachedEffect extends PreventionEffectImpl {

    protected AttachmentType attachmentType;

    public PreventDamageToAttachedEffect(Duration duration, AttachmentType attachmentType, boolean combatOnly) {
        this(duration, attachmentType, Integer.MAX_VALUE, combatOnly);
    }

    public PreventDamageToAttachedEffect(Duration duration, AttachmentType attachmentType, int amountToPrevent, boolean combatOnly) {
        super(duration, amountToPrevent, combatOnly, false);
        this.attachmentType = attachmentType;
        staticText = setText();
    }

    public PreventDamageToAttachedEffect(final PreventDamageToAttachedEffect effect) {
        super(effect);
        this.attachmentType = effect.attachmentType;
    }

    @Override
    public PreventDamageToAttachedEffect copy() {
        return new PreventDamageToAttachedEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (!onlyCombat || ((DamageEvent) event).isCombatDamage()) {
                Permanent attachment = game.getPermanent(source.getSourceId());
                if (attachment != null
                        && attachment.getAttachedTo() != null) {
                    if (event.getTargetId().equals(attachment.getAttachedTo())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder();
        if (amountToPrevent == Integer.MAX_VALUE) {
            sb.append("prevent all ");
            if (onlyCombat) {
                sb.append("combat ");
            }
            sb.append("damage that would be dealt to ");
            sb.append(attachmentType.verb()).append(" creature");
        } else {
            sb.append("If a source would deal ");
            if (onlyCombat) {
                sb.append("combat ");
            }
            sb.append("damage to ");
            sb.append(attachmentType.verb());
            sb.append("creature, prevent ").append(amountToPrevent);;
            sb.append("of that damage");
        }
        return sb.toString();
    }
}
