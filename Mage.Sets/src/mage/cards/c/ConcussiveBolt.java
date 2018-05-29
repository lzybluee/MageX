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
import mage.abilities.Ability;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author North
 */
public final class ConcussiveBolt extends CardImpl {

    public ConcussiveBolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Concussive Bolt deals 4 damage to target player.
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        // Metalcraft - If you control three or more artifacts, creatures that player controls can't block this turn.
        this.getSpellAbility().addEffect(new ConcussiveBoltEffect());
        this.getSpellAbility().addEffect(new ConcussiveBoltRestrictionEffect());
    }

    public ConcussiveBolt(final ConcussiveBolt card) {
        super(card);
    }

    @Override
    public ConcussiveBolt copy() {
        return new ConcussiveBolt(this);
    }
}

class ConcussiveBoltEffect extends OneShotEffect {

    public ConcussiveBoltEffect() {
        super(Outcome.Benefit);
        this.staticText = "Metalcraft - If you control three or more artifacts, creatures controlled by that player or by that planeswalker’s controller can’t block this turn.";
    }

    public ConcussiveBoltEffect(final ConcussiveBoltEffect effect) {
        super(effect);
    }

    @Override
    public ConcussiveBoltEffect copy() {
        return new ConcussiveBoltEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        source.getEffects().get(2).setValue("MetalcraftConcussiveBolt", MetalcraftCondition.instance.apply(game, source));
        return true;
    }
}

class ConcussiveBoltRestrictionEffect extends RestrictionEffect {

    public ConcussiveBoltRestrictionEffect() {
        super(Duration.EndOfTurn);
    }

    public ConcussiveBoltRestrictionEffect(final ConcussiveBoltRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public ConcussiveBoltRestrictionEffect copy() {
        return new ConcussiveBoltRestrictionEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        boolean metalcraft = (Boolean) this.getValue("MetalcraftConcussiveBolt");
        Player player = game.getPlayerOrPlaneswalkerController(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        if (metalcraft && permanent.getControllerId().equals(player.getId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return false;
    }
}
