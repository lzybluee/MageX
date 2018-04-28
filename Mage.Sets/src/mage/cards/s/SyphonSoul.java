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
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;

/**
 *
 * @author Loki
 */
public class SyphonSoul extends CardImpl {

    public SyphonSoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");


        // Syphon Soul deals 2 damage to each other player. You gain life equal to the damage dealt this way.
        this.getSpellAbility().addEffect(new SyphonSoulEffect());
    }

    public SyphonSoul(final SyphonSoul card) {
        super(card);
    }

    @Override
    public SyphonSoul copy() {
        return new SyphonSoul(this);
    }
}

class SyphonSoulEffect extends OneShotEffect {
    public SyphonSoulEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals 2 damage to each other player. You gain life equal to the damage dealt this way";
    }

    public SyphonSoulEffect(final SyphonSoulEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int damageDealt = 0;
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            if (!playerId.equals(source.getControllerId())) {
                damageDealt += game.getPlayer(playerId).damage(2, source.getSourceId(), game, false, true);
            }
        }
        if (damageDealt > 0) {
            game.getPlayer(source.getControllerId()).gainLife(damageDealt, game, source);
        }
        return true;
    }

    @Override
    public SyphonSoulEffect copy() {
        return new SyphonSoulEffect(this);
    }

}