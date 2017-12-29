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
package mage.cards.i;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

/**
 * @author fireshoes
 */
public class Insist extends CardImpl {

    public Insist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // The next creature spell you cast this turn can't be countered by spells or abilities.
        this.getSpellAbility().addEffect(new InsistEffect());
        this.getSpellAbility().addWatcher(new InsistWatcher());

        // Draw a card.
        Effect effect = new DrawCardSourceControllerEffect(1);
        effect.setText("<br><br>Draw a card");
        this.getSpellAbility().addEffect(effect);
    }

    public Insist(final Insist card) {
        super(card);
    }

    @Override
    public Insist copy() {
        return new Insist(this);
    }
}

class InsistEffect extends ContinuousRuleModifyingEffectImpl {

    InsistEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "The next creature spell you cast this turn can't be countered by spells or abilities";
    }

    InsistEffect(final InsistEffect effect) {
        super(effect);
    }

    @Override
    public InsistEffect copy() {
        return new InsistEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        InsistWatcher watcher = (InsistWatcher) game.getState().getWatchers().get(InsistWatcher.class.getSimpleName(), source.getControllerId());
        if (watcher != null) {
            watcher.setReady();
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject != null) {
            return "This spell can't be countered (" + sourceObject.getName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        InsistWatcher watcher = (InsistWatcher) game.getState().getWatchers().get(InsistWatcher.class.getSimpleName(), source.getControllerId());
        return spell != null && watcher != null && watcher.isUncounterable(spell.getId());
    }
}

class InsistWatcher extends Watcher {

    protected boolean ready = false;
    protected UUID uncounterableSpell;

    InsistWatcher() {
        super(InsistWatcher.class.getSimpleName(), WatcherScope.PLAYER);
    }

    InsistWatcher(final InsistWatcher watcher) {
        super(watcher);
        this.uncounterableSpell = watcher.uncounterableSpell;
    }

    @Override
    public InsistWatcher copy() {
        return new InsistWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST && ready) {
            if (uncounterableSpell == null && event.getPlayerId().equals(this.getControllerId())) {
                Spell spell = game.getStack().getSpell(event.getTargetId());
                if (spell != null && (spell.isCreature())) {
                    uncounterableSpell = spell.getId();
                    ready = false;
                }
            }
        }
    }

    public boolean isUncounterable(UUID spellId) {
        return spellId.equals(uncounterableSpell);
    }

    public void setReady() {
        ready = true;
    }
}