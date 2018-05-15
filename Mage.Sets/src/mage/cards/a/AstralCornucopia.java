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
package mage.cards.a;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class AstralCornucopia extends CardImpl {

    public AstralCornucopia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{X}{X}{X}");

        // Astral Cornucopia enters the battlefield with X charge counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.CHARGE.createInstance())));

        // {T}: Choose a color. Add one mana of that color for each charge counter on Astral Cornucopia.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new AstralCornucopiaManaEffect(), new TapSourceCost()));
    }

    public AstralCornucopia(final AstralCornucopia card) {
        super(card);
    }

    @Override
    public AstralCornucopia copy() {
        return new AstralCornucopia(this);
    }
}

class AstralCornucopiaManaEffect extends ManaEffect {

    private final Mana computedMana;

    public AstralCornucopiaManaEffect() {
        super();
        computedMana = new Mana();
        this.staticText = "Choose a color. Add one mana of that color for each charge counter on {this}";
    }

    public AstralCornucopiaManaEffect(final AstralCornucopiaManaEffect effect) {
        super(effect);
        this.computedMana = effect.computedMana.copy();
    }

    @Override
    public AstralCornucopiaManaEffect copy() {
        return new AstralCornucopiaManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            checkToFirePossibleEvents(getMana(game, source), game, source);
            controller.getManaPool().addMana(getMana(game, source), game, source);
            return true;

        }
        return false;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        Mana mana = new Mana();
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            int counters = sourcePermanent.getCounters(game).getCount(CounterType.CHARGE.getName());
            if (counters > 0) {
                if (netMana) {
                    return new Mana(0, 0, 0, 0, 0, 0, counters, 0);
                }
                Player controller = game.getPlayer(source.getControllerId());
                if (controller != null) {
                    ChoiceColor choice = new ChoiceColor();
                    choice.setMessage("Choose a color to add mana of that color");
                    if (controller.choose(outcome, choice, game)) {
                        if (choice.getChoice() != null) {
                            String color = choice.getChoice();
                            switch (color) {
                                case "Red":
                                    mana.setRed(counters);
                                    break;
                                case "Blue":
                                    mana.setBlue(counters);
                                    break;
                                case "White":
                                    mana.setWhite(counters);
                                    break;
                                case "Black":
                                    mana.setBlack(counters);
                                    break;
                                case "Green":
                                    mana.setGreen(counters);
                                    break;
                            }
                        }
                    }
                }
            }
        }

        return mana;
    }

}
