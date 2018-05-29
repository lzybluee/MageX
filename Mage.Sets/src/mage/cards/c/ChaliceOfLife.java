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
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author intimidatingant
 */
public final class ChaliceOfLife extends CardImpl {

    public ChaliceOfLife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        this.transformable = true;
        this.secondSideCardClazz = ChaliceOfDeath.class;
        this.addAbility(new TransformAbility());


        // {tap}: You gain 1 life. Then if you have at least 10 life more than your starting life total, transform Chalice of Life.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ChaliceOfLifeEffect(), new TapSourceCost()));
    }

    public ChaliceOfLife(final ChaliceOfLife card) {
        super(card);
    }

    @Override
    public ChaliceOfLife copy() {
        return new ChaliceOfLife(this);
    }
}

class ChaliceOfLifeEffect extends OneShotEffect {

    public ChaliceOfLifeEffect() {
        super(Outcome.GainLife);
        staticText = "You gain 1 life. Then if you have at least 10 life more than your starting life total, transform Chalice of Life";
    }

    public ChaliceOfLifeEffect(final ChaliceOfLifeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            Player player = game.getPlayer(source.getControllerId());
            //gain 1 life
            player.gainLife(1, game, source);

            // if you have at least 10 life more than your starting life total, transform Chalice of Life.
            if (player.getLife() >= game.getLife() + 10) {
                permanent.transform(game);
                game.informPlayers(new StringBuilder(permanent.getName()).append(" transforms into ").append(permanent.getSecondCardFace().getName()).toString());
            }
        }
        return false;
    }

    @Override
    public ChaliceOfLifeEffect copy() {
        return new ChaliceOfLifeEffect(this);
    }

}
