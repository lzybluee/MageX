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
package mage.cards.c;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.NameACardEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class CursedScroll extends CardImpl {

    public CursedScroll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {3}, {T}: Name a card. Reveal a card at random from your hand. If it's the named card, Cursed Scroll deals 2 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new NameACardEffect(NameACardEffect.TypeOfName.ALL), new ManaCostsImpl("{3}"));
        ability.addEffect(new CursedScrollEffect());
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    public CursedScroll(final CursedScroll card) {
        super(card);
    }

    @Override
    public CursedScroll copy() {
        return new CursedScroll(this);
    }
}

class CursedScrollEffect extends OneShotEffect {

    public CursedScrollEffect() {
        super(Outcome.Neutral);
        staticText = "Reveal a card at random from your hand. If it's the named card, {this} deals 2 damage to any target";
    }

    public CursedScrollEffect(final CursedScrollEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + NameACardEffect.INFO_KEY);
        if (sourceObject != null && controller != null && cardName != null && !cardName.isEmpty()) {
            if (!controller.getHand().isEmpty()) {
                Cards revealed = new CardsImpl();
                Card card = controller.getHand().getRandom(game);
                if (card == null) {
                    return false;
                }
                revealed.add(card);
                controller.revealCards(sourceObject.getIdName(), revealed, game);
                if (card.getName().equals(cardName)) {
                    Permanent creature = game.getPermanent(targetPointer.getFirst(game, source));
                    if (creature != null) {
                        creature.damage(2, source.getSourceId(), game, false, true);
                        return true;
                    }
                    Player player = game.getPlayer(targetPointer.getFirst(game, source));
                    if (player != null) {
                        player.damage(2, source.getSourceId(), game, false, true);
                        return true;
                    }
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public CursedScrollEffect copy() {
        return new CursedScrollEffect(this);
    }
}
