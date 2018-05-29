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
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class FriendlyFire extends CardImpl {

    public FriendlyFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");

        // Target creature's controller reveals a card at random from their hand. Friendly Fire deals damage to that creature and that player equal to the revealed card's converted mana cost.
        this.getSpellAbility().addEffect(new FriendlyFireEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    public FriendlyFire(final FriendlyFire card) {
        super(card);
    }

    @Override
    public FriendlyFire copy() {
        return new FriendlyFire(this);
    }
}

class FriendlyFireEffect extends OneShotEffect {

    public FriendlyFireEffect() {
        super(Outcome.Discard);
        this.staticText = "Target creature's controller reveals a card at random from their hand. {this} deals damage to that creature and that player equal to the revealed card's converted mana cost";
    }

    public FriendlyFireEffect(final FriendlyFireEffect effect) {
        super(effect);
    }

    @Override
    public FriendlyFireEffect copy() {
        return new FriendlyFireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetCreature != null) {
                Player controllerOfTargetCreature = game.getPlayer(targetCreature.getControllerId());
                if (controllerOfTargetCreature != null) {
                    if (!controllerOfTargetCreature.getHand().isEmpty()) {
                        Cards cards = new CardsImpl();
                        Card card = controllerOfTargetCreature.getHand().getRandom(game);
                        if (card != null) {
                            cards.add(card);
                            controllerOfTargetCreature.revealCards(sourceObject.getName(), cards, game);
                            int damage = card.getConvertedManaCost();
                            targetCreature.damage(damage, source.getSourceId(), game, false, true);
                            controllerOfTargetCreature.damage(damage, source.getSourceId(), game, false, true);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
