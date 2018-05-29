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

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class WretchedBanquet extends CardImpl {

    public WretchedBanquet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Destroy target creature if it has the least power or is tied for least power among creatures on the battlefield.
        this.getSpellAbility().addEffect(new WretchedBanquetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public WretchedBanquet(final WretchedBanquet card) {
        super(card);
    }

    @Override
    public WretchedBanquet copy() {
        return new WretchedBanquet(this);
    }
}

class WretchedBanquetEffect extends OneShotEffect {

    public WretchedBanquetEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target creature if it has the least power or is tied for least power among creatures on the battlefield";
    }

    public WretchedBanquetEffect(final WretchedBanquetEffect effect) {
        super(effect);
    }

    @Override
    public WretchedBanquetEffect copy() {
        return new WretchedBanquetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        List<Permanent> creatures = game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source.getSourceId(), game);

        int minPower = targetCreature.getPower().getValue() + 1;
        for (Permanent creature : creatures) {
            if (minPower > creature.getPower().getValue()) {
                minPower = creature.getPower().getValue();
            }
        }

        if (targetCreature != null && targetCreature.getPower().getValue() <= minPower) {
            targetCreature.destroy(source.getSourceId(), game, false);
            return true;
        }
        return false;
    }
}
