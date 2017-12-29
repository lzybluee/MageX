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

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public class CopperhornScout extends CardImpl {

    public CopperhornScout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new CopperhornScoutTriggeredAbility());
    }

    public CopperhornScout(final CopperhornScout card) {
        super(card);
    }

    @Override
    public CopperhornScout copy() {
        return new CopperhornScout(this);
    }
}

class CopperhornScoutTriggeredAbility extends TriggeredAbilityImpl {

    public CopperhornScoutTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CopperhornScoutUntapEffect(), false);
    }

    public CopperhornScoutTriggeredAbility(final CopperhornScoutTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CopperhornScoutTriggeredAbility copy() {
        return new CopperhornScoutTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever {this} attacks, untap each other creature you control.";
    }
}

class CopperhornScoutUntapEffect extends OneShotEffect {

    CopperhornScoutUntapEffect ( ) {
        super(Outcome.Untap);
    }

    CopperhornScoutUntapEffect ( CopperhornScoutUntapEffect effect ) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

        List<Permanent> creatures = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game);

        for ( Permanent creature : creatures ) {
            if ( !creature.getId().equals(source.getSourceId()) ) {
                creature.untap(game);
            }
        }

        return true;
    }

    @Override
    public CopperhornScoutUntapEffect copy() {
        return new CopperhornScoutUntapEffect(this);
    }

}
