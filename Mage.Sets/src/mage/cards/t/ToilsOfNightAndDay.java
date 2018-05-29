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

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class ToilsOfNightAndDay extends CardImpl {

    public ToilsOfNightAndDay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");
        this.subtype.add(SubType.ARCANE);

        // You may tap or untap target permanent, then you may tap or untap another target permanent.
        this.getSpellAbility().addEffect(new ToilsOfNightAndDayEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(0, 2, new FilterPermanent(), false));
    }

    public ToilsOfNightAndDay(final ToilsOfNightAndDay card) {
        super(card);
    }

    @Override
    public ToilsOfNightAndDay copy() {
        return new ToilsOfNightAndDay(this);
    }


    private static class ToilsOfNightAndDayEffect extends OneShotEffect {

        public ToilsOfNightAndDayEffect() {
            super(Outcome.Tap);
            this.staticText = "You may tap or untap target permanent, then you may tap or untap another target permanent";
        }

        public ToilsOfNightAndDayEffect(final ToilsOfNightAndDayEffect effect) {
            super(effect);
        }

        @Override
        public ToilsOfNightAndDayEffect copy() {
            return new ToilsOfNightAndDayEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                for (UUID targetId : source.getTargets().get(0).getTargets()) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null) {
                        if (player.chooseUse(Outcome.Tap, new StringBuilder("Tap ").append(permanent.getName()).append('?').toString(), source, game)) {
                            permanent.tap(game);
                        } else if (player.chooseUse(Outcome.Untap, new StringBuilder("Untap ").append(permanent.getName()).append('?').toString(), source, game)) {
                            permanent.untap(game);
                        }
                    }
                }
                return true;
            }
            return false;
        }
    }
}