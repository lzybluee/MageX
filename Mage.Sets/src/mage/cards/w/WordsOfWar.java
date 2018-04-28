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
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author emerald000
 */
public class WordsOfWar extends CardImpl {

    public WordsOfWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");


        // {1}: The next time you would draw a card this turn, Words of War deals 2 damage to any target instead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new WordsOfWarEffect(), new GenericManaCost(1));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    public WordsOfWar(final WordsOfWar card) {
        super(card);
    }

    @Override
    public WordsOfWar copy() {
        return new WordsOfWar(this);
    }
}

class WordsOfWarEffect extends ReplacementEffectImpl {
    
    WordsOfWarEffect() {
        super(Duration.EndOfTurn, Outcome.Damage);
        staticText = "The next time you would draw a card this turn, {this} deals 2 damage to any target instead.";
    }
    
    WordsOfWarEffect(final WordsOfWarEffect effect) {
        super(effect);
    }
    
    @Override
    public WordsOfWarEffect copy() {
        return new WordsOfWarEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
    
    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Player player = game.getPlayer(targetPointer.getFirst(game, source));
            if (player != null) {
                player.damage(2, source.getSourceId(), game, false, true);
				this.used = true;
                discard();
                return true;
            }
            Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
            if (permanent != null) {
                permanent.damage(2, source.getSourceId(), game, false, true);
				this.used = true;
                discard();
                return true;
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
        if (!this.used) {
			return source.getControllerId().equals(event.getPlayerId());
        }
        return false;
    }
}
