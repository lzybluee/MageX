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
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class DuskmantleSeer extends CardImpl {

    public DuskmantleSeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // At the beginning of your upkeep, each player reveals the top card of their library, loses life equal to that card's converted mana cost, then puts it into their hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new DuskmantleSeerEffect(), TargetController.YOU, false, false));

    }

    public DuskmantleSeer(final DuskmantleSeer card) {
        super(card);
    }

    @Override
    public DuskmantleSeer copy() {
        return new DuskmantleSeer(this);
    }
}

class DuskmantleSeerEffect extends OneShotEffect {

    public DuskmantleSeerEffect() {
        super(Outcome.Detriment);
        this.staticText = "each player reveals the top card of their library, loses life equal to that card's converted mana cost, then puts it into their hand";
    }

    public DuskmantleSeerEffect(final DuskmantleSeerEffect effect) {
        super(effect);
    }

    @Override
    public DuskmantleSeerEffect copy() {
        return new DuskmantleSeerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourceCard = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourceCard == null) {
            return false;
        }
        for (Player player : game.getPlayers().values()) {
            if (player.getLibrary().hasCards()) {
                Card card = player.getLibrary().getFromTop(game);
                if (card != null) {
                    player.revealCards(source, ": Revealed by " + player.getName(), new CardsImpl(card), game);
                    player.loseLife(card.getConvertedManaCost(), game, false);
                    player.moveCards(card, Zone.HAND, source, game);
                }
            }
        }
        return true;
    }
}
