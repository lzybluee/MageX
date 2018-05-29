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
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class RuricTharTheUnbowed extends CardImpl {

    public RuricTharTheUnbowed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        
        // Reach
        this.addAbility(ReachAbility.getInstance());
        
        // Ruric Thar, the Unbowed attacks each turn if able.
        this.addAbility(new AttacksEachCombatStaticAbility());
        
        // Whenever a player casts a noncreature spell, Ruric Thar deals 6 damage to that player.
        this.addAbility(new RuricTharTheUnbowedAbility());
    }

    public RuricTharTheUnbowed(final RuricTharTheUnbowed card) {
        super(card);
    }

    @Override
    public RuricTharTheUnbowed copy() {
        return new RuricTharTheUnbowed(this);
    }
}

class RuricTharTheUnbowedAbility extends TriggeredAbilityImpl {

    public RuricTharTheUnbowedAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(6), false);
    }

    public RuricTharTheUnbowedAbility(final RuricTharTheUnbowedAbility ability) {
        super(ability);
    }

    @Override
    public RuricTharTheUnbowedAbility copy() {
        return new RuricTharTheUnbowedAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && !spell.isCreature()) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                }
                return true;
            }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player casts a noncreature spell, Ruric Thar deals 6 damage to that player.";
    }
}
