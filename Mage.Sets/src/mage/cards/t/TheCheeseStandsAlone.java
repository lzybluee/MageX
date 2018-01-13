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

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public class TheCheeseStandsAlone extends CardImpl {

    public TheCheeseStandsAlone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}{W}");

        // When you control no permanents other than The Cheese Stands Alone and have no cards in hand, you win the game.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new CheeseStandsAloneContinuousEffect());
        this.addAbility(ability);
    }

    public TheCheeseStandsAlone(final TheCheeseStandsAlone card) {
        super(card);
    }

    @Override
    public TheCheeseStandsAlone copy() {
        return new TheCheeseStandsAlone(this);
    }
}

class CheeseStandsAloneContinuousEffect extends ContinuousRuleModifyingEffectImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    private boolean wonAlready = false;
    static {
        filter.add(new NamePredicate("The Cheese Stands Alone"));
    }

    public CheeseStandsAloneContinuousEffect() {
        super(Duration.EndOfGame, Outcome.Benefit, false, false);
        staticText = "When you control no permanents other than {this} and have no cards in hand, you win the game";
    }

    public CheeseStandsAloneContinuousEffect(final CheeseStandsAloneContinuousEffect effect) {
        super(effect);        
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.getHand().isEmpty()) {
                int numberPerms = new PermanentsOnBattlefieldCount(new FilterControlledPermanent()).calculate(game, source, this);
                if (numberPerms == 1) {
                    if (game.getBattlefield().contains(filter, source.getControllerId(), 1, game)) {                        
                        if (!wonAlready) {
                            wonAlready = true;
                            controller.won(game);
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public CheeseStandsAloneContinuousEffect copy() {
        return new CheeseStandsAloneContinuousEffect(this);
    }
}
