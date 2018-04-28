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
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author jeffwadsworth
 */
public class GhastlordOfFugue extends CardImpl {

    public GhastlordOfFugue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U/B}{U/B}{U/B}{U/B}{U/B}");
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Ghastlord of Fugue can't be blocked.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedSourceEffect(Duration.WhileOnBattlefield)));

        // Whenever Ghastlord of Fugue deals combat damage to a player, that player reveals their hand. You choose a card from it. That player exiles that card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new GhastlordOfFugueEffect(), false, true));

    }

    public GhastlordOfFugue(final GhastlordOfFugue card) {
        super(card);
    }

    @Override
    public GhastlordOfFugue copy() {
        return new GhastlordOfFugue(this);
    }
}

class GhastlordOfFugueEffect extends OneShotEffect {

    public GhastlordOfFugueEffect() {
        super(Outcome.Benefit);
        staticText = "that player reveals their hand. You choose a card from it. That player exiles that card";
    }

    public GhastlordOfFugueEffect(final GhastlordOfFugueEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (targetPlayer != null
                && sourceObject != null
                && controller != null) {

            // reveal hand of target player 
            targetPlayer.revealCards(sourceObject.getName(), targetPlayer.getHand(), game);

            // You choose a card from it
            TargetCardInHand target = new TargetCardInHand(new FilterCard());
            target.setNotTarget(true);
            Card chosenCard = null;
            if (controller.choose(Outcome.Benefit, targetPlayer.getHand(), target, game)) {
                chosenCard = game.getCard(target.getFirstTarget());
            }
            if (chosenCard != null) {
                controller.moveCards(chosenCard, Zone.EXILED, source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public GhastlordOfFugueEffect copy() {
        return new GhastlordOfFugueEffect(this);
    }

}
