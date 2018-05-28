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
package mage.abilities.effects.common;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author LevelX2
 *
 */
public class HideawayPlayEffect extends OneShotEffect {

    public HideawayPlayEffect() {
        super(Outcome.Benefit);
        staticText = "You may play the exiled card without paying its mana cost";
    }

    public HideawayPlayEffect(final HideawayPlayEffect effect) {
        super(effect);
    }

    @Override
    public HideawayPlayEffect copy() {
        return new HideawayPlayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ExileZone zone = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source));
        if (zone == null || zone.isEmpty()) {
            return true;
        }
        Card card = zone.getCards(game).iterator().next();
        Player controller = game.getPlayer(source.getControllerId());
        if (card != null && controller != null) {
            if (controller.chooseUse(Outcome.PlayForFree, "Do you want to play " + card.getIdName() + " for free now?", source, game)) {
                card.setFaceDown(false, game);
                int zcc = card.getZoneChangeCounter(game);

                /* 702.74. Hideaway, rulings:
                 * If the removed card is a land, you may play it as a result of the last ability only if it's your turn
                 * and you haven't already played a land that turn. This counts as your land play for the turn.
                 */
                if (card.isLand()) {
                    UUID playerId = controller.getId();
                    if (!game.getActivePlayerId().equals(playerId) || !game.getPlayer(playerId).canPlayLand()) {
                        return false;
                    }
                }

                if (!controller.playCard(card, game, true, true, new MageObjectReference(source.getSourceObject(game), game))) {
                    if (card.getZoneChangeCounter(game) == zcc) {
                        card.setFaceDown(true, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
