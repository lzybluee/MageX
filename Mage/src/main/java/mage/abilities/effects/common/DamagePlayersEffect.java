/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.abilities.effects.common;

import java.util.UUID;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DamagePlayersEffect extends OneShotEffect {
    private DynamicValue amount;
    private TargetController controller;
    private String sourceName = "{source}";

    public DamagePlayersEffect(int amount) {
        this(Outcome.Damage, new StaticValue(amount));
    }

    public DamagePlayersEffect(int amount, TargetController controller) {
        this(Outcome.Damage, new StaticValue(amount), controller);
    }

    public DamagePlayersEffect(int amount, TargetController controller, String whoDealDamageName) {
        this(Outcome.Damage, new StaticValue(amount), controller);

        this.sourceName = whoDealDamageName;
        setText(); // TODO: replace to @Override public String getText()
    }

    public DamagePlayersEffect(Outcome outcome, DynamicValue amount) {
        this(outcome, amount, TargetController.ANY);
    }

    public DamagePlayersEffect(Outcome outcome, DynamicValue amount, TargetController controller) {
        super(outcome);
        this.amount = amount;
        this.controller = controller;
        setText();
    }


    public DamagePlayersEffect(final DamagePlayersEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.controller = effect.controller;
        this.sourceName = effect.sourceName;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        switch (controller) {
            case ANY:
                for (UUID playerId: game.getState().getPlayersInRange(source.getControllerId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.damage(amount.calculate(game, source, this), source.getSourceId(), game, false, true);
                    }
                }
                break;
            case OPPONENT:
                for (UUID playerId: game.getOpponents(source.getControllerId())) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.damage(amount.calculate(game, source, this), source.getSourceId(), game, false, true);
                    }
                }
                break;
            default:
                throw new UnsupportedOperationException("TargetController type not supported.");
        }
        return true;
    }

    @Override
    public DamagePlayersEffect copy() {
        return new DamagePlayersEffect(this);
    }

    private void setText()
    {
        StringBuilder sb = new StringBuilder().append(this.sourceName).append(" deals ").append(amount.toString());
        switch (controller) {
            case ANY:
                sb.append(" damage to each player");
                break;
            case OPPONENT:
                sb.append(" damage to each opponent");
                break;
            default:
                throw new UnsupportedOperationException("TargetController type not supported.");
        }
        staticText = sb.toString();
    }

}
