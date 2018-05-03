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

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class ReturnToBattlefieldUnderOwnerControlSourceEffect extends OneShotEffect {

    private boolean tapped;
    private boolean attacking;
    private int zoneChangeCounter;

    public ReturnToBattlefieldUnderOwnerControlSourceEffect() {
        this(false);
    }

    public ReturnToBattlefieldUnderOwnerControlSourceEffect(boolean tapped) {
        this(tapped, -1);
    }

    public ReturnToBattlefieldUnderOwnerControlSourceEffect(boolean tapped, int zoneChangeCounter) {
        this(tapped, false, zoneChangeCounter);
    }

    public ReturnToBattlefieldUnderOwnerControlSourceEffect(boolean tapped, boolean attacking, int zoneChangeCounter) {
        super(Outcome.Benefit);
        this.tapped = tapped;
        this.attacking = attacking;
        this.zoneChangeCounter = zoneChangeCounter;
        staticText = "return that card to the battlefield"
                + (tapped ? " tapped" : "")
                + (attacking ? " attacking" : "")
                + " under its owner's control";
    }

    public ReturnToBattlefieldUnderOwnerControlSourceEffect(final ReturnToBattlefieldUnderOwnerControlSourceEffect effect) {
        super(effect);
        this.tapped = effect.tapped;
        this.zoneChangeCounter = effect.zoneChangeCounter;
        this.attacking = effect.attacking;
    }

    @Override
    public ReturnToBattlefieldUnderOwnerControlSourceEffect copy() {
        return new ReturnToBattlefieldUnderOwnerControlSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        if (controller != null && card != null) {
            // return only from public zones
            switch (game.getState().getZone(card.getId())) {
                case EXILED:
                case COMMAND:
                case GRAVEYARD:
                    if (zoneChangeCounter < 0 || game.getState().getZoneChangeCounter(card.getId()) == zoneChangeCounter) {

                        if (controller.moveCards(card, Zone.BATTLEFIELD, source, game, tapped, false, true, null)) {
                            if (attacking) {
                                game.getCombat().addAttackingCreature(card.getId(), game);
                            }
                        }
                    }
                    break;
            }
            return true;
        }
        return false;
    }
}
