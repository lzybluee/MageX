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
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 * @author BetaSteward_at_googlemail.com
 *
 */
public class CopyTargetSpellEffect extends OneShotEffect {

    private final boolean useController;
    private final boolean useLKI;

    public CopyTargetSpellEffect() {
        this(false);
    }

    public CopyTargetSpellEffect(boolean useLKI) {
        this(false, useLKI);
    }

    public CopyTargetSpellEffect(boolean useController, boolean useLKI) {
        super(Outcome.Copy);
        this.useController = useController;
        this.useLKI = useLKI;
    }

    public CopyTargetSpellEffect(final CopyTargetSpellEffect effect) {
        super(effect);
        this.useLKI = effect.useLKI;
        this.useController = effect.useController;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell;
        if (useLKI) {
            spell = game.getSpellOrLKIStack(targetPointer.getFirst(game, source));
        } else {
            spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        }
        if (spell == null) {
            spell = (Spell) game.getLastKnownInformation(targetPointer.getFirst(game, source), Zone.STACK);
        }
        if (spell != null) {
            StackObject newStackObject = spell.createCopyOnStack(game, source, useController ? spell.getControllerId() : source.getControllerId(), true);
            Player player = game.getPlayer(source.getControllerId());
            if (player != null && newStackObject != null && newStackObject instanceof Spell) {
                String activateMessage = ((Spell) newStackObject).getActivatedMessage(game);
                if (activateMessage.startsWith(" casts ")) {
                    activateMessage = activateMessage.substring(6);
                }
                if (!game.isSimulation()) {
                    game.informPlayers(player.getLogName() + activateMessage);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public CopyTargetSpellEffect copy() {
        return new CopyTargetSpellEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("copy target ").append(mode.getTargets().get(0).getTargetName()).append(". You may choose new targets for the copy");
        return sb.toString();
    }
}
