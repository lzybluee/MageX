/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.effects.common;

import java.util.ArrayList;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CreateTokenEffect extends OneShotEffect {

    private Token token;
    private DynamicValue amount;
    private boolean tapped;
    private boolean attacking;
    private UUID lastAddedTokenId;
    private ArrayList<UUID> lastAddedTokenIds = new ArrayList<>();

    public CreateTokenEffect(Token token) {
        this(token, new StaticValue(1));
    }

    public CreateTokenEffect(Token token, int amount) {
        this(token, new StaticValue(amount));
    }

    public CreateTokenEffect(Token token, DynamicValue amount) {
        this(token, amount, false, false);
    }

    public CreateTokenEffect(Token token, int amount, boolean tapped, boolean attacking) {
        this(token, new StaticValue(amount), tapped, attacking);
    }

    public CreateTokenEffect(Token token, DynamicValue amount, boolean tapped, boolean attacking) {
        super(Outcome.PutCreatureInPlay);
        this.token = token;
        this.amount = amount.copy();
        this.tapped = tapped;
        this.attacking = attacking;
        setText();
    }

    public CreateTokenEffect(final CreateTokenEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
        this.token = effect.token.copy();
        this.tapped = effect.tapped;
        this.attacking = effect.attacking;
        this.lastAddedTokenId = effect.lastAddedTokenId;
        this.lastAddedTokenIds.addAll(effect.lastAddedTokenIds);
    }

    @Override
    public CreateTokenEffect copy() {
        return new CreateTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int value = amount.calculate(game, source, this);
        token.putOntoBattlefield(value, game, source.getSourceId(), source.getControllerId(), tapped, attacking);
        this.lastAddedTokenId = token.getLastAddedToken();
        this.lastAddedTokenIds = token.getLastAddedTokenIds();

        return true;
    }

    public UUID getLastAddedTokenId() {
        return lastAddedTokenId;
    }

    public ArrayList<UUID> getLastAddedTokenIds() {
        return lastAddedTokenIds;
    }

    public void exileTokensCreatedAtNextEndStep(Game game, Ability source) {
        for (UUID tokenId : this.getLastAddedTokenIds()) {
            Permanent tokenPermanent = game.getPermanent(tokenId);
            if (tokenPermanent != null) {
                ExileTargetEffect exileEffect = new ExileTargetEffect(null, "", Zone.BATTLEFIELD);
                exileEffect.setTargetPointer(new FixedTarget(tokenPermanent, game));
                game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect), source);
            }
        }
    }

    public void exileTokensCreatedAtEndOfCombat(Game game, Ability source) {
        for (UUID tokenId : this.getLastAddedTokenIds()) {
            Permanent tokenPermanent = game.getPermanent(tokenId);
            if (tokenPermanent != null) {
                ExileTargetEffect exileEffect = new ExileTargetEffect(null, "", Zone.BATTLEFIELD);
                exileEffect.setTargetPointer(new FixedTarget(tokenPermanent, game));
                game.addDelayedTriggeredAbility(new AtTheEndOfCombatDelayedTriggeredAbility(exileEffect), source);
            }
        }
    }

    private void setText() {
        StringBuilder sb = new StringBuilder("create ");
        if (amount.toString().equals("1")) {
            sb.append("a ");
            if (tapped && !attacking) {
                sb.append("tapped ");
            }
            sb.append(token.getDescription());
        } else {
            sb.append(CardUtil.numberToText(amount.toString())).append(' ');
            if (tapped && !attacking) {
                sb.append("tapped ");
            }
            sb.append(token.getDescription());
            if (token.getDescription().endsWith("token")) {
                sb.append("s");
            }
            int tokenLocation = sb.indexOf("token ");
            if (tokenLocation != -1) {
                sb.replace(tokenLocation, tokenLocation + 6, "tokens ");
            }
        }
        if (attacking) {
            sb.append(" that are");
            if (tapped) {
                sb.append(" tapped and");
            }
            sb.append(" attacking");
        }
        String message = amount.getMessage();
        if (!message.isEmpty()) {
            if (amount.toString().equals("X")) {
                sb.append(", where X is ");
            } else {
                sb.append(" for each ");
            }
        }
        sb.append(message);
        staticText = sb.toString();
    }
}
