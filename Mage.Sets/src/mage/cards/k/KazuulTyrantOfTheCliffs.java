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
package mage.cards.k;

import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
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
import mage.game.permanent.Permanent;
import mage.game.permanent.token.OgreToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class KazuulTyrantOfTheCliffs extends CardImpl {

    public KazuulTyrantOfTheCliffs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Whenever a creature an opponent controls attacks, if you're the defending player, create a 3/3 red Ogre creature token unless that creature's controller pays {3}.
        this.addAbility(new KazuulTyrantOfTheCliffsTriggeredAbility());
    }

    public KazuulTyrantOfTheCliffs(final KazuulTyrantOfTheCliffs card) {
        super(card);
    }

    @Override
    public KazuulTyrantOfTheCliffs copy() {
        return new KazuulTyrantOfTheCliffs(this);
    }
}

class KazuulTyrantOfTheCliffsTriggeredAbility extends TriggeredAbilityImpl {

    public KazuulTyrantOfTheCliffsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new KazuulTyrantOfTheCliffsEffect(new GenericManaCost(3)));
    }

    public KazuulTyrantOfTheCliffsTriggeredAbility(final KazuulTyrantOfTheCliffsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KazuulTyrantOfTheCliffsTriggeredAbility copy() {
        return new KazuulTyrantOfTheCliffsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent attacker = game.getPermanent(event.getSourceId());
        Player defender = game.getPlayer(event.getTargetId());
        Player you = game.getPlayer(controllerId);
        if (!Objects.equals(attacker.getControllerId(), you.getId()) && Objects.equals(defender, you)) {
            this.getEffects().get(0).setTargetPointer(new FixedTarget(attacker.getControllerId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature an opponent controls attacks, if you're the defending player, create a 3/3 red Ogre creature token unless that creature's controller pays {3}";
    }
}

class KazuulTyrantOfTheCliffsEffect extends OneShotEffect {

    protected Cost cost;
    private static OgreToken token = new OgreToken();

    public KazuulTyrantOfTheCliffsEffect(Cost cost) {
        super(Outcome.PutCreatureInPlay);
        this.cost = cost;
    }

    public KazuulTyrantOfTheCliffsEffect(KazuulTyrantOfTheCliffsEffect effect) {
        super(effect);
        this.cost = effect.cost.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player payee = game.getPlayer(targetPointer.getFirst(game, source));
        if (payee != null) {
            cost.clearPaid();
            if (!cost.pay(source, game, source.getSourceId(), payee.getId(), false, null)) {
                return token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
            }
        }
        return false;
    }

    @Override
    public KazuulTyrantOfTheCliffsEffect copy() {
        return new KazuulTyrantOfTheCliffsEffect(this);
    }
}
