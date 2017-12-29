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
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 * @author magenoxx_at_gmail.com
 */
public class Worship extends CardImpl {

    public Worship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}");

        // If you control a creature, damage that would reduce your life total to less than 1 reduces it to 1 instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new WorshipReplacementEffect()));
    }

    public Worship(final Worship card) {
        super(card);
    }

    @Override
    public Worship copy() {
        return new Worship(this);
    }
}

class WorshipReplacementEffect extends ReplacementEffectImpl {

    public WorshipReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you control a creature, damage that would reduce your life total to less than 1 reduces it to 1 instead";
    }

    public WorshipReplacementEffect(final WorshipReplacementEffect effect) {
        super(effect);
    }

    @Override
    public WorshipReplacementEffect copy() {
        return new WorshipReplacementEffect(this);
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_CAUSES_LIFE_LOSS;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (source.getControllerId().equals(event.getPlayerId())) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null
                    && (controller.getLife() - event.getAmount()) < 1
                    && game.getBattlefield().count(new FilterControlledCreaturePermanent(), source.getSourceId(), event.getPlayerId(), game) > 0
                    ) {
                event.setAmount(controller.getLife() - 1);
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return false;
    }
}
