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
package mage.cards.r;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class ReverseTheSands extends CardImpl {

    public ReverseTheSands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{W}{W}");

        // Redistribute any number of players' life totals.
        this.getSpellAbility().addEffect(new ReverseTheSandsEffect());
    }

    public ReverseTheSands(final ReverseTheSands card) {
        super(card);
    }

    @Override
    public ReverseTheSands copy() {
        return new ReverseTheSands(this);
    }
}

class ReverseTheSandsEffect extends OneShotEffect {

    public ReverseTheSandsEffect() {
        super(Outcome.Benefit);
        this.staticText = "Redistribute any number of players' life totals";
    }

    public ReverseTheSandsEffect(final ReverseTheSandsEffect effect) {
        super(effect);
    }

    @Override
    public ReverseTheSandsEffect copy() {
        return new ReverseTheSandsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Choice lifeChoice = new ChoiceImpl(true);
            Set<String> choices = new HashSet<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    choices.add(new StringBuilder(Integer.toString(player.getLife())).append(" life of ").append(player.getLogName()).toString());
                }
            }
            lifeChoice.setChoices(choices);
            for (UUID playersId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playersId);
                if (player != null) {
                    String selectedChoice;
                    if (choices.size() > 1) {
                        lifeChoice.setMessage("Which players life should get player " + player.getLogName());
                        if (!controller.choose(Outcome.Detriment, lifeChoice, game)) {
                            return false;
                        }
                        selectedChoice = lifeChoice.getChoice();
                    } else {
                        selectedChoice = choices.iterator().next();
                    }
                    int index = selectedChoice.indexOf(' ');
                    if (index > 0) {
                        String lifeString = selectedChoice.substring(0, index);
                        int life = Integer.parseInt(lifeString);
                        player.setLife(life, game, source);
                        choices.remove(selectedChoice);
                        game.informPlayers(new StringBuilder("Player ").append(player.getLogName()).append(" life set to ").append(life).toString());
                    }
                }
            }
        }
        return false;
    }
}
