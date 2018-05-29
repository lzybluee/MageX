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

import java.util.Objects;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class SharedTrauma extends CardImpl {

    public SharedTrauma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}");


        // Join forces - Starting with you, each player may pay any amount of mana. Each player puts the top X cards of their library into their graveyard, where X is the total amount of mana paid this way.
        this.getSpellAbility().addEffect(new SharedTraumaEffect());
    }

    public SharedTrauma(final SharedTrauma card) {
        super(card);
    }

    @Override
    public SharedTrauma copy() {
        return new SharedTrauma(this);
    }
}

class SharedTraumaEffect extends OneShotEffect {

    public SharedTraumaEffect() {
        super(Outcome.Detriment);
        this.staticText = "<i>Join forces</i> &mdash; Starting with you, each player may pay any amount of mana. Each player puts the top X cards of their library into their graveyard, where X is the total amount of mana paid this way";
    }

    public SharedTraumaEffect(final SharedTraumaEffect effect) {
        super(effect);
    }

    @Override
    public SharedTraumaEffect copy() {
        return new SharedTraumaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int xSum = 0;
            xSum += playerPaysXGenericMana(controller, source, game);
            for(UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                if (!Objects.equals(playerId, controller.getId())) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        xSum += playerPaysXGenericMana(player, source, game);

                    }
                }
            }
            if (xSum > 0) {
                for(UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    Effect effect = new PutTopCardOfLibraryIntoGraveTargetEffect(xSum);
                    effect.setTargetPointer(new FixedTarget(playerId));
                    effect.apply(game, source);
                }

            }
            // prevent undo
            controller.resetStoredBookmark(game);
            return true;
        }
        return false;
    }

    protected static int playerPaysXGenericMana(Player player, Ability source, Game game) {
        int xValue = 0;
        boolean payed = false;
        while (player.canRespond() && !payed) {
            xValue = player.announceXMana(0, Integer.MAX_VALUE, "How much mana will you pay?", game, source);
            if (xValue > 0) {
                Cost cost = new GenericManaCost(xValue);
                payed = cost.pay(source, game, source.getSourceId(), player.getId(), false, null);
            } else {
                payed = true;
            }
        }
        game.informPlayers(new StringBuilder(player.getLogName()).append(" pays {").append(xValue).append("}.").toString());
        return xValue;
    }
}

