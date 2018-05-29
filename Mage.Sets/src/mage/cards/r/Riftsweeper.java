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

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.FaceDownPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInExile;

/**
 *
 * @author LevelX2
 */
public final class Riftsweeper extends CardImpl {

    private static final FilterCard filter = new FilterCard("face-up exiled card");
    static {
        filter.add(Predicates.not(new FaceDownPredicate()));
    }

    public Riftsweeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Riftsweeper enters the battlefield, choose target face-up exiled card. Its owner shuffles it into their library.
        Ability ability = new EntersBattlefieldTriggeredAbility(new RiftsweeperEffect(), false);
        ability.addTarget(new TargetCardInExile(1, 1, filter, null, true));
        this.addAbility(ability);
    }

    public Riftsweeper(final Riftsweeper card) {
        super(card);
    }

    @Override
    public Riftsweeper copy() {
        return new Riftsweeper(this);
    }
}

class RiftsweeperEffect extends OneShotEffect {

    public RiftsweeperEffect() {
        super(Outcome.Benefit);
        this.staticText = "choose target face-up exiled card. Its owner shuffles it into their library";
    }

    public RiftsweeperEffect(final RiftsweeperEffect effect) {
        super(effect);
    }

    @Override
    public RiftsweeperEffect copy() {
        return new RiftsweeperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(targetPointer.getFirst(game, source));
        if (card != null) {
            // remove exiting suspend counters
            card.getCounters(game).clear();
            // move to exile
            card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
            game.getPlayer(card.getOwnerId()).shuffleLibrary(source, game);
            game.informPlayers(new StringBuilder("Riftsweeper: Choosen card was ").append(card.getName()).toString());
            return true;
        }
        return false;
    }
}
