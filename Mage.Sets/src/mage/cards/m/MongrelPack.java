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
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TurnPhase;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;

/**
 *
 * @author North
 */
public final class MongrelPack extends CardImpl {

    public MongrelPack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.HOUND);

        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // When Mongrel Pack dies during combat, create four 1/1 green Hound creature tokens.
        this.addAbility(new MongrelPackAbility());
    }

    public MongrelPack(final MongrelPack card) {
        super(card);
    }

    @Override
    public MongrelPack copy() {
        return new MongrelPack(this);
    }
}

class MongrelPackAbility extends ZoneChangeTriggeredAbility {

    public MongrelPackAbility() {
        super(Zone.BATTLEFIELD, Zone.GRAVEYARD, new CreateTokenEffect(new HoundToken(), 4), "When {this} dies during combat, ", false);
    }

    public MongrelPackAbility(MongrelPackAbility ability) {
        super(ability);
    }

    @Override
    public MongrelPackAbility copy() {
        return new MongrelPackAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            if (game.getPhase().getType() == TurnPhase.COMBAT) {
                return true;
            }
        }
        return false;
    }
}

class HoundToken extends TokenImpl {

    public HoundToken() {
        super("Hound", "1/1 green Hound creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.HOUND);

        color.setGreen(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public HoundToken(final HoundToken token) {
        super(token);
    }

    public HoundToken copy() {
        return new HoundToken(this);
    }
}
