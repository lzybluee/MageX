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

package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 *
 * @author LevelX
 */
public final class FeastOfWorms extends CardImpl {

    public FeastOfWorms (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}{G}");
        this.subtype.add(SubType.ARCANE);


        // Destroy target land. If that land was legendary, its controller sacrifices another land.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetLandPermanent());
        this.getSpellAbility().addEffect(new FeastOfWormsEffect());
    }

    public FeastOfWorms (final FeastOfWorms card) {
        super(card);
    }

    @Override
    public FeastOfWorms copy() {
        return new FeastOfWorms(this);
    }

}

class FeastOfWormsEffect extends OneShotEffect {

    FeastOfWormsEffect() {
        super(Outcome.Sacrifice);
        staticText = "If that land was legendary, its controller sacrifices another land";
    }

    FeastOfWormsEffect(FeastOfWormsEffect effect) {
        super(effect);
    }

    @Override
    public FeastOfWormsEffect copy() {
        return new FeastOfWormsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(id);
        Player targetPlayer = null;
        if (permanent != null) {
            targetPlayer = game.getPlayer(permanent.getControllerId());
        }
        if (targetPlayer != null && permanent != null && (permanent.isLegendary())) {
            FilterControlledPermanent filter = new FilterControlledLandPermanent("land to sacrifice");
            filter.add(new ControllerIdPredicate(targetPlayer.getId()));
            TargetControlledPermanent target = new TargetControlledPermanent(1, 1, filter, false);

            if (target.canChoose(targetPlayer.getId(), game)) {
                targetPlayer.chooseTarget(Outcome.Sacrifice, target, source, game);
                Permanent land = game.getPermanent(target.getFirstTarget());
                if (land != null) {
                    land.sacrifice(source.getSourceId(), game);
                }
            }
            return true;            
        }
        return false;
    }
}
