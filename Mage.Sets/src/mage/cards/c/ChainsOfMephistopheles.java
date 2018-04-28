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
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.CardsDrawnDuringDrawStepWatcher;

/**
 *
 * @author LevelX2
 */
public class ChainsOfMephistopheles extends CardImpl {

    public ChainsOfMephistopheles(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}");

        // If a player would draw a card except the first one he or she draws in their draw step each turn, that player discards a card instead. If the player discards a card this way, he or she draws a card. If the player doesn't discard a card this way, he or she puts the top card of their library into their graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ChainsOfMephistophelesReplacementEffect()), new CardsDrawnDuringDrawStepWatcher());
    }

    public ChainsOfMephistopheles(final ChainsOfMephistopheles card) {
        super(card);
    }

    @Override
    public ChainsOfMephistopheles copy() {
        return new ChainsOfMephistopheles(this);
    }
}

class ChainsOfMephistophelesReplacementEffect extends ReplacementEffectImpl {

    public ChainsOfMephistophelesReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a player would draw a card except the first one he or she draws in their draw step each turn, that player discards a card instead. If the player discards a card this way, he or she draws a card. If the player doesn't discard a card this way, he or she puts the top card of their library into their graveyard";
    }

    public ChainsOfMephistophelesReplacementEffect(final ChainsOfMephistophelesReplacementEffect effect) {
        super(effect);
    }

    @Override
    public ChainsOfMephistophelesReplacementEffect copy() {
        return new ChainsOfMephistophelesReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player != null) {
            if (player.getHand().isEmpty()) {
                // he or she puts the top card of their library into their graveyard
                Effect effect = new PutTopCardOfLibraryIntoGraveTargetEffect(1);
                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                effect.apply(game, source);
                return true;
            } else  {
                // discards a card instead. If the player discards a card this way, he or she draws a card.
                player.discard(1, false, source, game);
                return false; // because player draws a card, the draw event is kept
            }
        }
        return false;
    }
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    } 
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getActivePlayerId().equals(event.getPlayerId()) && game.getPhase().getStep().getType() == PhaseStep.DRAW) {
            CardsDrawnDuringDrawStepWatcher watcher = (CardsDrawnDuringDrawStepWatcher) game.getState().getWatchers().get(CardsDrawnDuringDrawStepWatcher.class.getSimpleName());
            if (watcher != null && watcher.getAmountCardsDrawn(event.getPlayerId()) > 0) {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }
}
