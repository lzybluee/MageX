/*
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
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 * Gatecrash FAQ (01.2013)
 * Multiple instances of lifelink are redundant. Giving the same creature lifelink
 * more than once won't cause you to gain additional life.
 *
 * Each time the second ability resolves, a delayed triggered ability is created.
 * Whenever you gain life that turn, each of those abilities will trigger. For
 * example, if you activate the second ability twice (and let those abilities resolve)
 * and then you gain 2 life, each opponent will lose a total of 4 life. Each instance
 * will cause two abilities to trigger, each causing that player to lose 2 life.
 *
 * @author LevelX2
 */
public final class VizkopaGuildmage extends CardImpl {

    public VizkopaGuildmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);


        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // 1{W}{B}: Target creature gains lifelink until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn),new ManaCostsImpl("{1}{W}{B}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // 1{W}{B}: Whenever you gain life this turn, each opponent loses that much life.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateDelayedTriggeredAbilityEffect(new VizkopaGuildmageDelayedTriggeredAbility()), new ManaCostsImpl("{1}{W}{B}")));

    }

    public VizkopaGuildmage(final VizkopaGuildmage card) {
        super(card);
    }

    @Override
    public VizkopaGuildmage copy() {
        return new VizkopaGuildmage(this);
    }
}

class VizkopaGuildmageDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public VizkopaGuildmageDelayedTriggeredAbility() {
        super(new OpponentsLoseLifeEffect(), Duration.EndOfTurn, false);
    }

    public VizkopaGuildmageDelayedTriggeredAbility(VizkopaGuildmageDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.GAINED_LIFE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(controllerId)) {
            getEffects().get(0).setValue("amountLifeGained", event.getAmount());
            return true;
        }
        return false;
    }

    @Override
    public VizkopaGuildmageDelayedTriggeredAbility copy() {
        return new VizkopaGuildmageDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you gain life this turn, " + modes.getText();
    }
}

class OpponentsLoseLifeEffect extends OneShotEffect {

    public OpponentsLoseLifeEffect() {
        super(Outcome.Damage);
        this.staticText = "each opponent loses that much life";
    }

    public OpponentsLoseLifeEffect(final OpponentsLoseLifeEffect effect) {
        super(effect);
    }

    @Override
    public OpponentsLoseLifeEffect copy() {
        return new OpponentsLoseLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Integer amountLifeGained = (Integer) this.getValue("amountLifeGained");
        if (amountLifeGained != null ) {
            for (UUID opponentId: game.getOpponents(source.getControllerId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    opponent.loseLife(amountLifeGained, game, false);
                }
            }
        }
        return false;
    }
}
