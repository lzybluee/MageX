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
package mage.cards.w;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class WardOfBones extends CardImpl {

    public WardOfBones(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // Each opponent who controls more creatures than you can't play creature cards. The same is true for artifacts, enchantments, and lands.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new WardOfBonesEffect()));

    }

    public WardOfBones(final WardOfBones card) {
        super(card);
    }

    @Override
    public WardOfBones copy() {
        return new WardOfBones(this);
    }
}

class WardOfBonesEffect extends ContinuousRuleModifyingEffectImpl {

    public WardOfBonesEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Each opponent who controls more creatures than you can't cast creature spells. "
                + "The same is true for artifacts and enchantments.<br><br>"
                + "Each opponent who controls more lands than you can't play lands.";
    }

    public WardOfBonesEffect(final WardOfBonesEffect effect) {
        super(effect);
    }

    @Override
    public WardOfBonesEffect copy() {
        return new WardOfBonesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject != null) {
            return "You can't play the land or cast the spell (" + mageObject.getLogName() + " in play).";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PLAY_LAND || event.getType() == GameEvent.EventType.CAST_SPELL;

    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            final Card card = game.getCard(event.getSourceId());
            final Player opponent = game.getPlayer(event.getPlayerId());
            if (card == null || opponent == null) {
                return false;
            }
            if (card.isCreature()
                    && game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_CREATURE, opponent.getId(), game)
                    > game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game)) {
                return true;
            }
            if (card.isArtifact()
                    && game.getBattlefield().countAll(new FilterArtifactPermanent(), opponent.getId(), game)
                    > game.getBattlefield().countAll(new FilterArtifactPermanent(), source.getControllerId(), game)) {
                return true;
            }
            if (card.isEnchantment()
                    && game.getBattlefield().countAll(StaticFilters.FILTER_ENCHANTMENT_PERMANENT, opponent.getId(), game)
                    > game.getBattlefield().countAll(StaticFilters.FILTER_ENCHANTMENT_PERMANENT, source.getControllerId(), game)) {
                return true;
            }
            final int yourLands = game.getBattlefield().countAll(new FilterLandPermanent(), source.getControllerId(), game);
            if (card.isLand()
                    && game.getBattlefield().countAll(new FilterLandPermanent(), opponent.getId(), game) > yourLands) {
                return true;
            }
        }
        return false;
    }
}
