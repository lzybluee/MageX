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
package mage.cards.s;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalReplacementEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventDamageToControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Quercitron
 */
public final class SpiritOfResistance extends CardImpl {

    public SpiritOfResistance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // As long as you control a permanent of each color, prevent all damage that would be dealt to you.
        Effect effect = new ConditionalReplacementEffect(
                new PreventDamageToControllerEffect(Duration.WhileOnBattlefield),
                SpiritOfResistanceCondition.instance);
        effect.setText("As long as you control a permanent of each color, prevent all damage that would be dealt to you.");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    public SpiritOfResistance(final SpiritOfResistance card) {
        super(card);
    }

    @Override
    public SpiritOfResistance copy() {
        return new SpiritOfResistance(this);
    }
}

enum SpiritOfResistanceCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<ObjectColor> colors = new HashSet<>();
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(controller.getId())) {
                if (permanent.getColor(game).isBlack()) {
                    colors.add(ObjectColor.BLACK);
                }
                if (permanent.getColor(game).isBlue()) {
                    colors.add(ObjectColor.BLUE);
                }
                if (permanent.getColor(game).isRed()) {
                    colors.add(ObjectColor.RED);
                }
                if (permanent.getColor(game).isGreen()) {
                    colors.add(ObjectColor.GREEN);
                }
                if (permanent.getColor(game).isWhite()) {
                    colors.add(ObjectColor.WHITE);
                }
            }
            return colors.size() >= 5;
        }
        return false;
    }

    @Override
    public String toString() {
        return "you control a permanent of each color";
    }
}
