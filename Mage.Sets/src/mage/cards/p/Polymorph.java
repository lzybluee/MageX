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
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Library;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class Polymorph extends CardImpl {

    public Polymorph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Destroy target creature. It can't be regenerated.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
        // Its controller reveals cards from the top of their library until he or she reveals a creature card.
        // The player puts that card onto the battlefield, then shuffles all other cards revealed this way into their library.
        this.getSpellAbility().addEffect(new PolymorphEffect());
    }

    public Polymorph(final Polymorph card) {
        super(card);
    }

    @Override
    public Polymorph copy() {
        return new Polymorph(this);
    }
}

class PolymorphEffect extends OneShotEffect {

    public PolymorphEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Its controller reveals cards from the top of their library until he or she reveals a creature card. The player puts that card onto the battlefield, then shuffles all other cards revealed this way into their library";
    }

    public PolymorphEffect(final PolymorphEffect effect) {
        super(effect);
    }

    @Override
    public PolymorphEffect copy() {
        return new PolymorphEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            Player player = game.getPlayer(permanent.getControllerId());
            if (player != null) {
                Library library = player.getLibrary();
                if (library.hasCards()) {
                    Cards cards = new CardsImpl();
                    Card toBattlefield = null;
                    for (Card card : library.getCards(game)) {
                        cards.add(card);
                        if (card.isCreature()) {
                            toBattlefield = card;
                            break;
                        }
                    }
                    if (toBattlefield != null) {
                        player.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game);
                    }
                    player.revealCards(source, cards, game);
                    cards.remove(toBattlefield);
                    if (!cards.isEmpty()) {
                        player.shuffleLibrary(source, game);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
