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
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 * @author LoneFox

 */
public final class EmbermawHellion extends CardImpl {

    public EmbermawHellion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.HELLION);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // If another red source you control would deal damage to a permanent or player, it deals that much damage plus 1 to that permanent or player instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new EmbermawHellionEffect()));
    }

    public EmbermawHellion(final EmbermawHellion card) {
        super(card);
    }

    @Override
    public EmbermawHellion copy() {
        return new EmbermawHellion(this);
    }
}

class EmbermawHellionEffect extends ReplacementEffectImpl {

    EmbermawHellionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If another red source you control would deal damage to a permanent or player, it deals that much damage plus 1 to that permanent or player instead.";
    }

    EmbermawHellionEffect(final EmbermawHellionEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch(event.getType()) {
            case DAMAGE_CREATURE:
            case DAMAGE_PLANESWALKER:
            case DAMAGE_PLAYER:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if(source.getControllerId().equals(game.getControllerId(event.getSourceId()))) {
            MageObject sourceObject;
            Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
            if(sourcePermanent == null) {
                sourceObject = game.getObject(event.getSourceId());
            }
            else {
                sourceObject = sourcePermanent;
            }
            return sourceObject != null && sourceObject.getColor(game).isRed() && !sourceObject.getId().equals(source.getSourceId());
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.addWithOverflowCheck(event.getAmount(), 1));
        return false;
    }

    @Override
    public EmbermawHellionEffect copy() {
        return new EmbermawHellionEffect(this);
    }

}
