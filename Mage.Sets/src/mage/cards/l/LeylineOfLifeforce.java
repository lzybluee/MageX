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
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.keyword.LeylineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;

/**
 *
 * @author emerald000
 */
public class LeylineOfLifeforce extends CardImpl {

    public LeylineOfLifeforce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}{G}");


        // If Leyline of Lifeforce is in your opening hand, you may begin the game with it on the battlefield.
        this.addAbility(LeylineAbility.getInstance());
        
        // Creature spells can't be countered.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LeylineOfLifeforceEffect()));
    }

    public LeylineOfLifeforce(final LeylineOfLifeforce card) {
        super(card);
    }

    @Override
    public LeylineOfLifeforce copy() {
        return new LeylineOfLifeforce(this);
    }
}

class LeylineOfLifeforceEffect extends ContinuousRuleModifyingEffectImpl {

    LeylineOfLifeforceEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Creature spells can't be countered";
    }

    LeylineOfLifeforceEffect(final LeylineOfLifeforceEffect effect) {
        super(effect);
    }

    @Override
    public LeylineOfLifeforceEffect copy() {
        return new LeylineOfLifeforceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.COUNTER;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && spell.isCreature()) {
            return true;
        }
        return false;
    }
}
