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
package mage.cards.n;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.y.YixlidJailer;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class NecroticOoze extends CardImpl {

    public NecroticOoze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.OOZE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // As long as Necrotic Ooze is on the battlefield, it has all activated abilities of all creature cards in all graveyards
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new NecroticOozeEffect()));
    }

    public NecroticOoze(final NecroticOoze card) {
        super(card);
    }

    @Override
    public NecroticOoze copy() {
        return new NecroticOoze(this);
    }

    static class NecroticOozeEffect extends ContinuousEffectImpl {

        public NecroticOozeEffect() {
            super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
            staticText = "As long as {this} is on the battlefield, it has all activated abilities of all creature cards in all graveyards";
        }

        public NecroticOozeEffect(final NecroticOozeEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent perm = game.getPermanent(source.getSourceId());
            if (perm != null) {
                for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        for (Card card : player.getGraveyard().getCards(game)) {
                            if (card.isCreature()) {
                                for (Ability ability : card.getAbilities()) {
                                    if (ability instanceof ActivatedAbility) {
                                        perm.addAbility(ability, game);
                                    }
                                }
                            }
                        }
                    }
                }
                return true;
            }
            return false;
        }

        @Override
        public NecroticOozeEffect copy() {
            return new NecroticOozeEffect(this);
        }

        @Override
        public Set<UUID> isDependentTo(List<ContinuousEffect> allEffectsInLayer) {
            // the dependent classes needs to be an enclosed class for dependent check of continuous effects
            return allEffectsInLayer.stream()
                    .filter(effect -> YixlidJailer.class.equals(effect.getClass().getEnclosingClass()))
                    .map(Effect::getId)
                    .collect(Collectors.toSet());

        }
    }

}
