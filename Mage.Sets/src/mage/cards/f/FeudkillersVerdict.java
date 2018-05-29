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
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.GiantWarriorToken;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class FeudkillersVerdict extends CardImpl {

    public FeudkillersVerdict(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.SORCERY}, "{4}{W}{W}");
        this.subtype.add(SubType.GIANT);

        // You gain 10 life. Then if you have more life than an opponent, create a 5/5 white Giant Warrior creature token.
        this.getSpellAbility().addEffect(new FeudkillersVerdictEffect());
    }

    public FeudkillersVerdict(final FeudkillersVerdict card) {
        super(card);
    }

    @Override
    public FeudkillersVerdict copy() {
        return new FeudkillersVerdict(this);
    }
}

class FeudkillersVerdictEffect extends OneShotEffect {

    public FeudkillersVerdictEffect() {
        super(Outcome.Benefit);
        this.staticText = "You gain 10 life. Then if you have more life than an opponent, create a 5/5 white Giant Warrior creature token";
    }

    public FeudkillersVerdictEffect(final FeudkillersVerdictEffect effect) {
        super(effect);
    }

    @Override
    public FeudkillersVerdictEffect copy() {
        return new FeudkillersVerdictEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.gainLife(10, game, source);
            boolean moreLife = false;
            for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    if (controller.getLife() > opponent.getLife()) {
                        moreLife = true;
                        break;
                    }
                }

            }
            if (moreLife) {
                return new CreateTokenEffect(new GiantWarriorToken(), 1).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
