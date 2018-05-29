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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.ChancellorAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author BetaSteward
 */
public final class ChancellorOfTheAnnex extends CardImpl {

    public ChancellorOfTheAnnex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // You may reveal this card from your opening hand. If you do, when each opponent casts their first spell of the game, counter that spell unless that player pays {1}.
        this.addAbility(new ChancellorAbility(new ChancellorOfTheAnnexEffect()));

        this.addAbility(FlyingAbility.getInstance());

        // Whenever an opponent casts a spell, counter it unless that player pays {1}.
        this.addAbility(new SpellCastOpponentTriggeredAbility(Zone.BATTLEFIELD, new CounterUnlessPaysEffect(new GenericManaCost(1)), StaticFilters.FILTER_SPELL, false, SetTargetPointer.SPELL));
    }

    public ChancellorOfTheAnnex(final ChancellorOfTheAnnex card) {
        super(card);
    }

    @Override
    public ChancellorOfTheAnnex copy() {
        return new ChancellorOfTheAnnex(this);
    }
}

class ChancellorOfTheAnnexEffect extends OneShotEffect {

    public ChancellorOfTheAnnexEffect() {
        super(Outcome.Benefit);
        staticText = "when each opponent casts their first spell of the game, counter that spell unless that player pays {1}";
    }

    public ChancellorOfTheAnnexEffect(ChancellorOfTheAnnexEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            DelayedTriggeredAbility ability = new ChancellorOfTheAnnexDelayedTriggeredAbility(opponentId);
            game.addDelayedTriggeredAbility(ability, source);
        }
        return true;
    }

    @Override
    public ChancellorOfTheAnnexEffect copy() {
        return new ChancellorOfTheAnnexEffect(this);
    }

}

class ChancellorOfTheAnnexDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final UUID playerId;

    ChancellorOfTheAnnexDelayedTriggeredAbility(UUID playerId) {
        super(new CounterUnlessPaysEffect(new GenericManaCost(1)));
        this.playerId = playerId;
    }

    ChancellorOfTheAnnexDelayedTriggeredAbility(final ChancellorOfTheAnnexDelayedTriggeredAbility ability) {
        super(ability);
        this.playerId = ability.playerId;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(playerId)) {
            this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getTargetId()));
            return true;
        }
        return false;
    }

    @Override
    public ChancellorOfTheAnnexDelayedTriggeredAbility copy() {
        return new ChancellorOfTheAnnexDelayedTriggeredAbility(this);
    }
}
