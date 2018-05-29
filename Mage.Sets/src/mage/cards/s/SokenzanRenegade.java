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
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.BushidoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class SokenzanRenegade extends CardImpl {

    public SokenzanRenegade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.SAMURAI);
        this.subtype.add(SubType.MERCENARY);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Bushido 1
        this.addAbility(new BushidoAbility(1));
        // At the beginning of your upkeep, if a player has more cards in hand than each other player, the player who has the most cards in hand gains control of Sokenzan Renegade.
        this.addAbility(new ConditionalTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new SokenzanRenegadeEffect(), TargetController.YOU, false),
                OnePlayerHasTheMostCards.instance,
                "At the beginning of your upkeep, if a player has more cards in hand than each other player, the player who has the most cards in hand gains control of {this}"
        ));

    }

    public SokenzanRenegade(final SokenzanRenegade card) {
        super(card);
    }

    @Override
    public SokenzanRenegade copy() {
        return new SokenzanRenegade(this);
    }
}

class SokenzanRenegadeEffect extends OneShotEffect {

    public SokenzanRenegadeEffect() {
        super(Outcome.GainControl);
        this.staticText = "the player who has the most cards in hand gains control of {this}";
    }

    public SokenzanRenegadeEffect(final SokenzanRenegadeEffect effect) {
        super(effect);
    }

    @Override
    public SokenzanRenegadeEffect copy() {
        return new SokenzanRenegadeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (controller != null
                && sourcePermanent != null) {
            int max = Integer.MIN_VALUE;
            Player newController = null;
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    if (player.getHand().size() > max) {
                        max = player.getHand().size();
                        newController = player;
                    }
                }
            }
            if (newController != null) {
                ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, newController.getId());
                effect.setTargetPointer(new FixedTarget(sourcePermanent.getId()));
                game.addEffect(effect, source);
                if (!source.getControllerId().equals(newController.getId())) {
                    game.informPlayers(newController.getLogName() + " got control of " + sourcePermanent.getLogName());
                }
                return true;
            }
        }
        return false;
    }
}

enum OnePlayerHasTheMostCards implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int max = Integer.MIN_VALUE;
            boolean onlyOnePlayer = false;
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    if (player.getHand().size() > max) {
                        max = player.getHand().size();
                        onlyOnePlayer = true;
                    } else if (player.getHand().size() == max) {
                        onlyOnePlayer = false;
                    }
                }
            }
            return onlyOnePlayer;
        }
        return false;
    }

    @Override
    public String toString() {
        return "a player has more cards in hand than each other player";
    }

}
