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

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class SavageVentmaw extends CardImpl {

    public SavageVentmaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{G}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Savage Ventmaw attacks, add {R}{R}{R}{G}{G}{G}. Until end of turn, you don’t lose this mana as steps and phases end.
        Effect effect = new SavageVentmawManaEffect(new Mana(3, 3, 0, 0, 0, 0, 0, 0), "your", true);
        effect.setText("add {R}{R}{R}{G}{G}{G}. Until end of turn, this mana doesn't empty from your mana pool as steps and phases end");
        this.addAbility(new AttacksTriggeredAbility(effect, false));

    }

    public SavageVentmaw(final SavageVentmaw card) {
        super(card);
    }

    @Override
    public SavageVentmaw copy() {
        return new SavageVentmaw(this);
    }
}

class SavageVentmawManaEffect extends ManaEffect {

    protected Mana mana;
    protected boolean emptyOnlyOnTurnsEnd;

    public SavageVentmawManaEffect(Mana mana, String textManaPoolOwner, boolean emptyOnTurnsEnd) {
        super();
        this.mana = mana;
        this.emptyOnlyOnTurnsEnd = emptyOnTurnsEnd;
        this.staticText = (textManaPoolOwner.equals("their") ? "that player adds " : "add ") + mana.toString() + " to " + textManaPoolOwner + " mana pool";
    }

    public SavageVentmawManaEffect(final SavageVentmawManaEffect effect) {
        super(effect);
        this.mana = effect.mana;
        this.emptyOnlyOnTurnsEnd = effect.emptyOnlyOnTurnsEnd;
    }

    @Override
    public SavageVentmawManaEffect copy() {
        return new SavageVentmawManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.getManaPool().addMana(getMana(game, source), game, source, emptyOnlyOnTurnsEnd);
            return true;
        }
        return false;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        return mana.copy();
    }

}
