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
package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.BeastToken2;
import mage.watchers.common.PermanentsEnteredBattlefieldWatcher;

import java.util.List;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class BalothCageTrap extends CardImpl {

    public BalothCageTrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}{G}");
        this.subtype.add(SubType.TRAP);

        // If an opponent had an artifact enter the battlefield under their control this turn, you may pay {1}{G} rather than pay Baloth Cage Trap's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ManaCostsImpl("{1}{G}"), BalothCageTrapCondition.instance), new PermanentsEnteredBattlefieldWatcher());

        // Create a 4/4 green Beast creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new BeastToken2()));
    }

    public BalothCageTrap(final BalothCageTrap card) {
        super(card);
    }

    @Override
    public BalothCageTrap copy() {
        return new BalothCageTrap(this);
    }
}

enum BalothCageTrapCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        PermanentsEnteredBattlefieldWatcher watcher = (PermanentsEnteredBattlefieldWatcher) game.getState().getWatchers().get(PermanentsEnteredBattlefieldWatcher.class.getSimpleName());
        if (watcher != null) {
            for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                List<Permanent> permanents = watcher.getThisTurnEnteringPermanents(opponentId);
                if (permanents != null) {
                    for (Permanent permanent : permanents) {
                        if (permanent.isArtifact()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "If an opponent had an artifact enter the battlefield under their control this turn";
    }
}
