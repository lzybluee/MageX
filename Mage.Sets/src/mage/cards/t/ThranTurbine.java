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
package mage.cards.t;

import mage.ConditionalMana;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.mana.AddConditionalColorlessManaEffect;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author spjspjs
 */
public class ThranTurbine extends CardImpl {

    public ThranTurbine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // At the beginning of your upkeep, you may add {C} or {C}{C}. You can't spend this mana to cast spells.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new ThranTurbineEffect(), TargetController.YOU, true));
    }

    public ThranTurbine(final ThranTurbine card) {
        super(card);
    }

    @Override
    public ThranTurbine copy() {
        return new ThranTurbine(this);
    }
}

class ThranTurbineEffect extends OneShotEffect {

    public ThranTurbineEffect() {
        super(Outcome.Benefit);
        staticText = "add {C}{C}. You can't spend this mana to cast spells";
    }

    public ThranTurbineEffect(final ThranTurbineEffect effect) {
        super(effect);
    }

    @Override
    public ThranTurbineEffect copy() {
        return new ThranTurbineEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));

        if (player != null) {
            new AddConditionalColorlessManaEffect(2, new ThranTurbineManaBuilder()).apply(game, source);
            return true;
        }
        return false;
    }
}

class ThranTurbineManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new ThranTurbineConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "You can't spend this mana to cast spells";
    }
}

class ThranTurbineConditionalMana extends ConditionalMana {

    public ThranTurbineConditionalMana(Mana mana) {
        super(mana);
        staticText = "You can't spend this mana to cast spells";
        addCondition(new ThranTurbineManaCondition());
    }
}

class ThranTurbineManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costToPay) {
        if (!(source instanceof Spell)) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
            return permanent != null;
        }
        return false;
    }
}
