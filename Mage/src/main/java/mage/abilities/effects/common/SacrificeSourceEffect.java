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

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SacrificeSourceEffect extends OneShotEffect {

    public SacrificeSourceEffect() {
        super(Outcome.Sacrifice);
        staticText = "sacrifice {this}";
    }

    public SacrificeSourceEffect(final SacrificeSourceEffect effect) {
        super(effect);
    }

    @Override
    public SacrificeSourceEffect copy() {
        return new SacrificeSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
        if (sourceObject == null) {
            // Check if the effect was installed by the spell the source was cast by (e.g. Necromancy), if not don't sacrifice the permanent
            if (source.getSourceObject(game) instanceof Spell) {
                sourceObject = game.getPermanent(source.getSourceId());
                if (sourceObject != null && sourceObject.getZoneChangeCounter(game) > source.getSourceObjectZoneChangeCounter() + 1) {
                    return false;
                }
            }
        }
        if (sourceObject instanceof Permanent) {
            Permanent permanent = (Permanent) sourceObject;
            // you can only sacrifice a permanent you control
            if (source.getControllerId().equals(permanent.getControllerId())) {
                return permanent.sacrifice(source.getSourceId(), game);
            }
            return true;
        }
        return false;
    }

}
