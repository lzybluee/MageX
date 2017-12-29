/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.effects.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ReturnFromExileEffect extends OneShotEffect {

    private UUID exileId;
    private Zone zone;
    private boolean tapped;

    public ReturnFromExileEffect(UUID exileId, Zone zone) {
        this(exileId, zone, false);
    }

    public ReturnFromExileEffect(UUID exileId, Zone zone, String text) {
        this(exileId, zone, false);
        staticText = text;
    }

    public ReturnFromExileEffect(UUID exileId, Zone zone, boolean tapped) {
        super(Outcome.PutCardInPlay);
        this.exileId = exileId;
        this.zone = zone;
        this.tapped = tapped;
        setText();
    }

    public ReturnFromExileEffect(final ReturnFromExileEffect effect) {
        super(effect);
        this.exileId = effect.exileId;
        this.zone = effect.zone;
        this.tapped = effect.tapped;
    }

    @Override
    public ReturnFromExileEffect copy() {
        return new ReturnFromExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ExileZone exile = game.getExile().getExileZone(exileId);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && exile != null) {
            switch (zone) {
                case LIBRARY:
                    controller.putCardsOnTopOfLibrary(exile, game, source, false);
                    break;
                default:
                    controller.moveCards(exile.getCards(game), zone, source, game, tapped, false, true, null);
            }
            return true;
        }
        return false;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append("return the exiled cards ");
        switch (zone) {
            case BATTLEFIELD:
                sb.append("to the battlefield under its owner's control");
                if (tapped) {
                    sb.append(" tapped");
                }
                break;
            case HAND:
                sb.append("to their owner's hand");
                break;
            case GRAVEYARD:
                sb.append("to their owner's graveyard");
                break;
        }
        staticText = sb.toString();
    }

}
