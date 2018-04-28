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
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PutLibraryIntoGraveTargetEffect extends OneShotEffect {

    private DynamicValue amount;

    public PutLibraryIntoGraveTargetEffect(int amount) {
        this(new StaticValue(amount));
    }

    public PutLibraryIntoGraveTargetEffect(DynamicValue amount) {
        super(Outcome.Detriment);
        this.amount = amount;
    }

    public PutLibraryIntoGraveTargetEffect(final PutLibraryIntoGraveTargetEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
    }

    public void setAmount(DynamicValue value) {
        this.amount = value;
    }

    @Override
    public PutLibraryIntoGraveTargetEffect copy() {
        return new PutLibraryIntoGraveTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            player.moveCards(player.getLibrary().getTopCards(game, amount.calculate(game, source, this)), Zone.GRAVEYARD, source, game);
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        String message = amount.getMessage();

        sb.append("target ").append(mode.getTargets().get(0).getTargetName());
        sb.append(" puts the top ");
        if (message.isEmpty()) {
            if (amount.toString().equals("1")) {
                sb.append("card ");
            } else {
                sb.append(CardUtil.numberToText(amount.toString())).append(" cards ");
            }
        } else {
            sb.append(" X cards ");
        }
        sb.append("of their library into their graveyard");

        if (!message.isEmpty()) {
            sb.append(", where X is the number of ");
            sb.append(message);
        }
        return sb.toString();
    }

}
