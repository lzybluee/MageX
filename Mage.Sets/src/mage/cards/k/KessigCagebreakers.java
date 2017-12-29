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
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.token.WolfToken;
import mage.players.Player;

/**
 *
 * @author North
 */
public class KessigCagebreakers extends CardImpl {

    public KessigCagebreakers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever Kessig Cagebreakers attacks, create a 2/2 green Wolf creature token tapped and attacking for each creature card in your graveyard.
        this.addAbility(new AttacksTriggeredAbility(new KessigCagebreakersEffect(), false));
    }

    public KessigCagebreakers(final KessigCagebreakers card) {
        super(card);
    }

    @Override
    public KessigCagebreakers copy() {
        return new KessigCagebreakers(this);
    }
}

class KessigCagebreakersEffect extends OneShotEffect {

    public KessigCagebreakersEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create a 2/2 green Wolf creature token tapped and attacking for each creature card in your graveyard";
    }

    public KessigCagebreakersEffect(final KessigCagebreakersEffect effect) {
        super(effect);
    }

    @Override
    public KessigCagebreakersEffect copy() {
        return new KessigCagebreakersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            WolfToken token = new WolfToken();
            int count = player.getGraveyard().count(new FilterCreatureCard(), game);
            for (int i = 0; i < count; i++) {
                token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId(), true, true);
            }
            return true;
        }
        return false;
    }
}
