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
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class MadcapExperiment extends CardImpl {

    public MadcapExperiment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Reveal cards from the top of your library until you reveal an artifact card. Put that card onto the battlefield and the rest on the bottom of your library in a random order. Madcap Experiment deals damage to you equal to the number of cards revealed this way.
        this.getSpellAbility().addEffect(new MadcapExperimentEffect());
    }

    public MadcapExperiment(final MadcapExperiment card) {
        super(card);
    }

    @Override
    public MadcapExperiment copy() {
        return new MadcapExperiment(this);
    }
}

class MadcapExperimentEffect extends OneShotEffect {

    public MadcapExperimentEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Reveal cards from the top of your library until you reveal an artifact card. Put that card onto the battlefield and the rest on the bottom of your library in a random order. {this} deals damage to you equal to the number of cards revealed this way";
    }

    public MadcapExperimentEffect(final MadcapExperimentEffect effect) {
        super(effect);
    }

    @Override
    public MadcapExperimentEffect copy() {
        return new MadcapExperimentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            CardsImpl toReveal = new CardsImpl();
            Card toBattlefield = null;
            for (Card card : controller.getLibrary().getCards(game)) {
                toReveal.add(card);
                game.fireUpdatePlayersEvent();
                if (card.isArtifact()) {
                    toBattlefield = card;

                    break;
                }
            }
            controller.revealCards(source, toReveal, game);
            controller.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game);
            int damage = toReveal.size();
            toReveal.remove(toBattlefield);
            controller.putCardsOnBottomOfLibrary(toReveal, game, source, false);
            controller.damage(damage, source.getSourceId(), game, false, true);

            return true;
        }
        return false;
    }
}
