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

import java.util.Locale;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.AttachmentType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author jeff
 */
public class RegenerateAttachedEffect extends ReplacementEffectImpl {

    protected AttachmentType attachmentType;

    public RegenerateAttachedEffect(AttachmentType attachmentType) {
        super(Duration.EndOfTurn, Outcome.Regenerate);
        this.attachmentType = attachmentType;
        this.setText();
    }

    public RegenerateAttachedEffect(final RegenerateAttachedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        //20110204 - 701.11
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        Permanent equipped = game.getPermanent(permanent.getAttachedTo());
        if (equipped != null && equipped.regenerate(this.getId(), game)) {
            this.used = true;
            return true;
        }
        return false;
    }

    @Override
    public RegenerateAttachedEffect copy() {
        return new RegenerateAttachedEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return apply(game, source);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DESTROY_PERMANENT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        //20110204 - 701.11c - event.getAmount() is used to signal if regeneration is allowed
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null) {
            Permanent equipped = game.getPermanent(equipment.getAttachedTo());
            if (equipped != null) {
                UUID equippedID = equipped.getId();
                if (event.getAmount() == 0 && event.getTargetId().equals(equippedID) && !this.used) {
                    return true;
                }
            }
        }
        return false;
    }

    private void setText() {
        staticText = "Regenerate " + attachmentType.verb().toLowerCase(Locale.ENGLISH) + " creature";
    }
}
