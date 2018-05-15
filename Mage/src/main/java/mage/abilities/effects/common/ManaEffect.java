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

import java.util.ArrayList;
import java.util.List;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.constants.AbilityType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ManaEvent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class ManaEffect extends OneShotEffect {

    protected Mana createdMana;

    public ManaEffect() {
        super(Outcome.PutManaInPool);
        createdMana = null;
    }

    public ManaEffect(final ManaEffect effect) {
        super(effect);
        this.createdMana = effect.createdMana == null ? null : effect.createdMana.copy();
    }

    /**
     * Creates the mana the effect can produce or if that already has happened
     * returns the mana the effect has created during its process of resolving
     *
     * @param game
     * @param source
     * @return
     */
    public Mana getMana(Game game, Ability source) {
        if (createdMana == null) {
            return createdMana = produceMana(false, game, source);
        }
        return createdMana;
    }

    /**
     * Returns the currently available max mana variations the effect can
     * produce
     *
     * @param game
     * @param source
     * @return
     */
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        Mana mana = produceMana(true, game, source);
        if (mana != null) {
            netMana.add(mana);
        }
        return netMana;
    }

    /**
     * Produced the mana the effect can produce
     *
     * @param netMana true - produce the hypotetical possible mana for check of
     * possible castable spells
     * @param game
     * @param source
     * @return
     */
    public abstract Mana produceMana(boolean netMana, Game game, Ability source);

    /**
     * Only used for mana effects that decide which kind of mana is produced
     * during resolution of the effect.
     *
     * @param mana
     * @param game
     * @param source
     */
    public void checkToFirePossibleEvents(Mana mana, Game game, Ability source) {
        if (source.getAbilityType() == AbilityType.MANA) {
            for (Cost cost : source.getCosts()) {
                if (cost instanceof TapSourceCost) {
                    ManaEvent event = new ManaEvent(GameEvent.EventType.TAPPED_FOR_MANA, source.getSourceId(), source.getSourceId(), source.getControllerId(), mana);
                    if (!game.replaceEvent(event)) {
                        game.fireEvent(event);
                    }
                }
            }
        }
    }
}
