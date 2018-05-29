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
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.CardsInControllerGraveCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainProtectionFromColorAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;

/**
 *
 * @author TheElk801
 */
public final class AvenWarcraft extends CardImpl {

    public AvenWarcraft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Creatures you control get +0/+2 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(0, 2, Duration.EndOfTurn));

        // Threshold - If seven or more cards are in your graveyard, choose a color. Creatures you control also gain protection from the chosen color until end of turn.
        this.getSpellAbility().addEffect(new AvenWarcraftEffect());
    }

    public AvenWarcraft(final AvenWarcraft card) {
        super(card);
    }

    @Override
    public AvenWarcraft copy() {
        return new AvenWarcraft(this);
    }
}

class AvenWarcraftEffect extends OneShotEffect {

    AvenWarcraftEffect() {
        super(Outcome.Benefit);
        this.staticText = "<br><br><i>Threshold</i> &mdash; If seven or more cards are in your graveyard, "
                + "choose a color. Creatures you control also gain protection from the chosen color until end of turn";
    }

    AvenWarcraftEffect(final AvenWarcraftEffect effect) {
        super(effect);
    }

    @Override
    public AvenWarcraftEffect copy() {
        return new AvenWarcraftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (new CardsInControllerGraveCondition(7).apply(game, source)) {
            game.addEffect(new GainProtectionFromColorAllEffect(
                    Duration.EndOfTurn,
                    StaticFilters.FILTER_CONTROLLED_CREATURES
            ), source);
        }
        return true;
    }
}
