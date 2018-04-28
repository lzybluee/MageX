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
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SkipDrawStepEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public class DamiaSageOfStone extends CardImpl {

    public DamiaSageOfStone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{G}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GORGON);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Skip your draw step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SkipDrawStepEffect()));

        // At the beginning of your upkeep, if you have fewer than seven cards in hand, draw cards equal to the difference.
        this.addAbility(new DamiaSageOfStoneTriggeredAbility());
    }

    public DamiaSageOfStone(final DamiaSageOfStone card) {
        super(card);
    }

    @Override
    public DamiaSageOfStone copy() {
        return new DamiaSageOfStone(this);
    }
}

class DamiaSageOfStoneTriggeredAbility extends BeginningOfUpkeepTriggeredAbility {

    DamiaSageOfStoneTriggeredAbility() {
        super(new DrawCardSourceControllerEffect(new IntPlusDynamicValue(7, new MultipliedValue(new CardsInControllerHandCount(), -1))), TargetController.YOU, false);
    }

    DamiaSageOfStoneTriggeredAbility(final DamiaSageOfStoneTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DamiaSageOfStoneTriggeredAbility copy() {
        return new DamiaSageOfStoneTriggeredAbility(this);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Player player = game.getPlayer(this.getControllerId());
        if (player != null) {
            return player.getHand().size() < 7;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of your upkeep, if you have fewer than seven cards in hand, draw cards equal to the difference";
    }
}
