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
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class BottledCloister extends CardImpl {

    public BottledCloister(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // At the beginning of each opponent's upkeep, exile all cards from your hand face down.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new BottledCloisterExileEffect(), TargetController.OPPONENT, false));
        // At the beginning of your upkeep, return all cards you own exiled with Bottled Cloister to your hand, then draw a card.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new BottledCloisterReturnEffect(), TargetController.YOU, false);
        Effect effect = new DrawCardSourceControllerEffect(1);
        effect.setText(", then draw a card");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public BottledCloister(final BottledCloister card) {
        super(card);
    }

    @Override
    public BottledCloister copy() {
        return new BottledCloister(this);
    }
}

class BottledCloisterExileEffect extends OneShotEffect {

    public BottledCloisterExileEffect() {
        super(Outcome.Detriment);
        this.staticText = "exile all cards from your hand face down";
    }

    public BottledCloisterExileEffect(final BottledCloisterExileEffect effect) {
        super(effect);
    }

    @Override
    public BottledCloisterExileEffect copy() {
        return new BottledCloisterExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            int numberOfCards = controller.getHand().size();
            if (numberOfCards > 0) {
                UUID exileId = CardUtil.getCardExileZoneId(game, source);
                for (Card card: controller.getHand().getCards(game)) {
                    card.moveToExile(exileId, sourcePermanent.getName(), source.getSourceId(), game);
                    card.setFaceDown(true, game);
                }
                game.informPlayers(sourcePermanent.getName() + ": " + controller.getLogName() + " exiles their hand face down (" + numberOfCards + "card" + (numberOfCards > 1 ?"s":"") + ')');
            }
            return true;
        }
        return false;
    }
}

class BottledCloisterReturnEffect extends OneShotEffect {

    public BottledCloisterReturnEffect() {
        super(Outcome.Benefit);
        this.staticText = "return all cards you own exiled with {this} to your hand";
    }

    public BottledCloisterReturnEffect(final BottledCloisterReturnEffect effect) {
        super(effect);
    }

    @Override
    public BottledCloisterReturnEffect copy() {
        return new BottledCloisterReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            UUID exileId = CardUtil.getCardExileZoneId(game, source);
            int numberOfCards = 0;
            ExileZone exileZone = game.getExile().getExileZone(exileId);
            if (exileZone != null) {
                for (Card card:  exileZone.getCards(game)) {
                    if (card.getOwnerId().equals(controller.getId())) {
                        numberOfCards++;
                        card.moveToZone(Zone.HAND, source.getSourceId(), game, true);
                        card.setFaceDown(false, game);
                    }
                }
            }
            if (numberOfCards > 0) {
                game.informPlayers(sourcePermanent.getLogName() + ": " + controller.getLogName() + " returns "+ numberOfCards + " card" + (numberOfCards > 1 ?"s":"") + " from exile to hand");
            }
            return true;
        }
        return false;
    }
}
