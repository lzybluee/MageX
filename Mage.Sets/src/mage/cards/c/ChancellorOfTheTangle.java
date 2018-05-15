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
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.ChancellorAbility;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author BetaSteward
 */
public class ChancellorOfTheTangle extends CardImpl {

    private static String abilityText = "at the beginning of your first main phase, add {G}";

    public ChancellorOfTheTangle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.color.setGreen(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(7);

        // You may reveal this card from your opening hand. If you do, at the beginning of your first main phase, add {G}.
        this.addAbility(new ChancellorAbility(new ChancellorOfTheTangleDelayedTriggeredAbility(), abilityText));

        this.addAbility(VigilanceAbility.getInstance());
        this.addAbility(ReachAbility.getInstance());
    }

    public ChancellorOfTheTangle(final ChancellorOfTheTangle card) {
        super(card);
    }

    @Override
    public ChancellorOfTheTangle copy() {
        return new ChancellorOfTheTangle(this);
    }
}

class ChancellorOfTheTangleDelayedTriggeredAbility extends DelayedTriggeredAbility {

    ChancellorOfTheTangleDelayedTriggeredAbility () {
        super(new BasicManaEffect(Mana.GreenMana(1)));
    }

    ChancellorOfTheTangleDelayedTriggeredAbility(ChancellorOfTheTangleDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.PRECOMBAT_MAIN_PHASE_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getActivePlayerId().equals(controllerId);
    }
    @Override
    public ChancellorOfTheTangleDelayedTriggeredAbility copy() {
        return new ChancellorOfTheTangleDelayedTriggeredAbility(this);
    }
}
