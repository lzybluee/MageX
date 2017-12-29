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
package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.TargetSpell;

import java.util.UUID;

public class OddsEnds extends SplitCard {

    public OddsEnds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{R}", "{3}{R}{W}", SpellAbilityType.SPLIT);

        // Odds
        // Flip a coin. If it comes up heads, counter target instant or sorcery spell. If it comes up tails, copy that spell and you may choose new targets for the copy.
        getLeftHalfCard().getSpellAbility().addEffect(new OddsEffect());
        getLeftHalfCard().getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_INSTANT_OR_SORCERY_SPELL));

        // Ends
        // Target player sacrifices two attacking creatures.
        getRightHalfCard().getSpellAbility().addEffect(new SacrificeEffect(StaticFilters.FILTER_ATTACKING_CREATURES, 2, "Target player"));
        getRightHalfCard().getSpellAbility().addTarget(new TargetPlayer());

    }

    public OddsEnds(final OddsEnds card) {
        super(card);
    }

    @Override
    public OddsEnds copy() {
        return new OddsEnds(this);
    }
}

class OddsEffect extends OneShotEffect {

    public OddsEffect() {
        super(Outcome.Benefit);
        this.staticText = "Flip a coin. If it comes up heads, counter target instant or sorcery spell. If it comes up tails, copy that spell and you may choose new targets for the copy";
    }

    public OddsEffect(final OddsEffect effect) {
        super(effect);
    }

    @Override
    public OddsEffect copy() {
        return new OddsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.flipCoin(game)) {
                game.informPlayers("Odds: Spell countered");
                return game.getStack().counter(getTargetPointer().getFirst(game, source), source.getSourceId(), game);

            } else {
                game.informPlayers("Odds: Spell will be copied");
                return new CopyTargetSpellEffect().apply(game, source);
            }
        }
        return false;
    }
}
