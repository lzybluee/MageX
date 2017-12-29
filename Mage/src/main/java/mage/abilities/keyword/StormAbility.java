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

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.watchers.common.CastSpellLastTurnWatcher;

/**
 *
 * @author Plopman
 */
public class StormAbility extends TriggeredAbilityImpl {

    public StormAbility() {
        super(Zone.STACK, new StormEffect());
    }

    private StormAbility(final StormAbility ability) {
        super(ability);
    }

    @Override
    public StormAbility copy() {
        return new StormAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(getSourceId())) {
            StackObject spell = game.getStack().getStackObject(getSourceId());
            if (spell instanceof Spell) {
                for (Effect effect : this.getEffects()) {
                    effect.setValue("StormSpell", spell);
                    effect.setValue("StormSpellRef", new MageObjectReference(spell.getId(), game));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Storm <i>(When you cast this spell, copy it for each spell cast before it this turn. You may choose new targets for the copies.)</i>";
    }
}

class StormEffect extends OneShotEffect {

    public StormEffect() {
        super(Outcome.Copy);
    }

    public StormEffect(final StormEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObjectReference spellRef = (MageObjectReference) this.getValue("StormSpellRef");
        if (spellRef != null) {
            CastSpellLastTurnWatcher watcher = (CastSpellLastTurnWatcher) game.getState().getWatchers().get(CastSpellLastTurnWatcher.class.getSimpleName());
            int stormCount = watcher.getSpellOrder(spellRef, game) - 1;
            if (stormCount > 0) {
                Spell spell = (Spell) this.getValue("StormSpell");
                if (spell != null) {
                    if (!game.isSimulation()) {
                        game.informPlayers("Storm: " + spell.getLogName() + " will be copied " + stormCount + " time" + (stormCount > 1 ? "s" : ""));
                    }
                    for (int i = 0; i < stormCount; i++) {
                        spell.createCopyOnStack(game, source, source.getControllerId(), true);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public StormEffect copy() {
        return new StormEffect(this);
    }
}
