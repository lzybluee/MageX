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
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.token.SoldierToken;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class DarienKingOfKjeldor extends CardImpl {

    public DarienKingOfKjeldor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you're dealt damage, you may create that many 1/1 white Soldier creature tokens.
        this.addAbility(new DarienKingOfKjeldorTriggeredAbility());
    }

    public DarienKingOfKjeldor(final DarienKingOfKjeldor card) {
        super(card);
    }

    @Override
    public DarienKingOfKjeldor copy() {
        return new DarienKingOfKjeldor(this);
    }
}

class DarienKingOfKjeldorTriggeredAbility extends TriggeredAbilityImpl {

    public DarienKingOfKjeldorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DarienKingOfKjeldorEffect(), true);
    }

    public DarienKingOfKjeldorTriggeredAbility(final DarienKingOfKjeldorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DarienKingOfKjeldorTriggeredAbility copy() {
        return new DarienKingOfKjeldorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if ((event.getTargetId().equals(this.getControllerId()))) {
            this.getEffects().get(0).setValue("damageAmount", event.getAmount());
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you're dealt damage, you may create that many 1/1 white Soldier creature tokens.";
    }
}

class DarienKingOfKjeldorEffect extends OneShotEffect {

    public DarienKingOfKjeldorEffect() {
        super(Outcome.Benefit);
    }

    public DarienKingOfKjeldorEffect(final DarienKingOfKjeldorEffect effect) {
        super(effect);
    }

    @Override
    public DarienKingOfKjeldorEffect copy() {
        return new DarienKingOfKjeldorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int damage = (Integer) this.getValue("damageAmount");
            return new CreateTokenEffect(new SoldierToken(), damage).apply(game, source);
        }
        return false;
    }
}
