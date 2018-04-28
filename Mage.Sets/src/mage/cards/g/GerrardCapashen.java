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
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.SourceAttackingCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author FenrisulfrX
 */
public class GerrardCapashen extends CardImpl {

    public GerrardCapashen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, you gain 1 life for each card in target opponent's hand.
        Ability ability1 = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new GerrardCapashenEffect(),
                TargetController.YOU, false, true);
        ability1.addTarget(new TargetOpponent());
        this.addAbility(ability1);

        // {3}{W}: Tap target creature. Activate this ability only if {this} is attacking.
        Ability ability2 = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(),
                new ManaCostsImpl("{3}{W}"), SourceAttackingCondition.instance);
        ability2.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability2);
    }

    public GerrardCapashen(final GerrardCapashen card) {
        super(card);
    }

    @Override
    public GerrardCapashen copy() {
        return new GerrardCapashen(this);
    }
}

class GerrardCapashenEffect extends OneShotEffect {

    public GerrardCapashenEffect() {
        super(Outcome.GainLife);
        staticText = "you gain 1 life for each card in target opponent's hand.";
    }

    public GerrardCapashenEffect(final GerrardCapashenEffect effect) {
        super(effect);
    }

    @Override
    public GerrardCapashenEffect copy() {
        return new GerrardCapashenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetOpponent = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (controller != null && targetOpponent != null) {
            int cardsInHand = targetOpponent.getHand().size();
            if (cardsInHand > 0) {
                controller.gainLife(cardsInHand, game, source);
            }
            return true;
        }
        return false;
    }
}
