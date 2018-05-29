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
package mage.cards.j;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public final class JackInTheMox extends CardImpl {

    public JackInTheMox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{0}");

        // {T}: Roll a six-sided die. This ability has the indicated effect.
        // 1 - Sacrifice Jack-in-the-Mox and you lose 5 life.
        // 2 - Add {W}.
        // 3 - Add {U}.
        // 4 - Add {B}.
        // 5 - Add {R}.
        // 6 - Add {G}.
        SimpleManaAbility ability = new SimpleManaAbility(Zone.BATTLEFIELD, new JackInTheMoxManaEffect(), new TapSourceCost());
        ability.setUndoPossible(false);
        this.addAbility(ability);
    }

    public JackInTheMox(final JackInTheMox card) {
        super(card);
    }

    @Override
    public JackInTheMox copy() {
        return new JackInTheMox(this);
    }
}

class JackInTheMoxManaEffect extends ManaEffect {

    JackInTheMoxManaEffect() {
        super();
        staticText = "Roll a six-sided die for {this}. On a 1, sacrifice {this} and lose 5 life. Otherwise, {this} has one of the following effects. Treat this ability as a mana source."
                + "<br/>2 Add {W}.\n"
                + "<br/>3 Add {U}.\n"
                + "<br/>4 Add {B}.\n"
                + "<br/>5 Add {R}.\n"
                + "<br/>6 Add {G}.";
    }

    JackInTheMoxManaEffect(final JackInTheMoxManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.getManaPool().addMana(getMana(game, source), game, source);
            return true;
        }
        return false;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            int amount = controller.rollDice(game, 6);
            Mana mana = new Mana();
            switch (amount) {
                case 1:
                    permanent.sacrifice(source.getSourceId(), game);
                    controller.loseLife(5, game, false);
                    break;
                case 2:
                    mana.add(Mana.WhiteMana(1));
                    break;
                case 3:
                    mana.add(Mana.BlueMana(1));
                    break;
                case 4:
                    mana.add(Mana.BlackMana(1));
                    break;
                case 5:
                    mana.add(Mana.RedMana(1));
                    break;
                case 6:
                    mana.add(Mana.GreenMana(1));
                    break;
                default:
                    break;
            }
            return mana;
        }
        return null;
    }

    @Override
    public JackInTheMoxManaEffect copy() {
        return new JackInTheMoxManaEffect(this);
    }
}
