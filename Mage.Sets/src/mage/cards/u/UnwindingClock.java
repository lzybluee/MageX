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
package mage.cards.u;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward
 */
public final class UnwindingClock extends CardImpl {

    public UnwindingClock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // Untap all artifacts you control during each other player's untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new UnwindingClockEffect()));
    }

    public UnwindingClock(final UnwindingClock card) {
        super(card);
    }

    @Override
    public UnwindingClock copy() {
        return new UnwindingClock(this);
    }
}

class UnwindingClockEffect extends ContinuousEffectImpl {

    private static FilterArtifactPermanent filter = new FilterArtifactPermanent();

    public UnwindingClockEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Untap all artifacts you control during each other player's untap step";
    }

    public UnwindingClockEffect(final UnwindingClockEffect effect) {
        super(effect);
    }

    @Override
    public UnwindingClockEffect copy() {
        return new UnwindingClockEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Boolean applied = (Boolean) game.getState().getValue(source.getSourceId() + "applied");
        if (applied == null) {
            applied = Boolean.FALSE;
        }
        if (!applied && layer == Layer.RulesEffects) {
            if (!game.getActivePlayerId().equals(source.getControllerId()) && game.getStep().getType() == PhaseStep.UNTAP) {
                game.getState().setValue(source.getSourceId() + "applied", true);
                for (Permanent artifact : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
                    boolean untap = true;
                    for (RestrictionEffect effect : game.getContinuousEffects().getApplicableRestrictionEffects(artifact, game).keySet()) {
                        untap &= effect.canBeUntapped(artifact, source, game);
                    }
                    if (untap) {
                        artifact.untap(game);
                    }
                }
            }
        } else if (applied && layer == Layer.RulesEffects) {
            if (game.getStep().getType() == PhaseStep.END_TURN) {
                game.getState().setValue(source.getSourceId() + "applied", false);
            }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}
