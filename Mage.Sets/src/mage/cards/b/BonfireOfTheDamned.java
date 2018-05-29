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

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MiracleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author noxx
 */
public final class BonfireOfTheDamned extends CardImpl {

    public BonfireOfTheDamned(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{R}");

        // Bonfire of the Damned deals X damage to target player and each creature he or she controls.
        this.getSpellAbility().addEffect(new BonfireOfTheDamnedEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Miracle {X}{R}
        this.addAbility(new MiracleAbility(this, new ManaCostsImpl("{X}{R}")));
    }

    public BonfireOfTheDamned(final BonfireOfTheDamned card) {
        super(card);
    }

    @Override
    public BonfireOfTheDamned copy() {
        return new BonfireOfTheDamned(this);
    }
}

class BonfireOfTheDamnedEffect extends OneShotEffect {

    private static FilterPermanent filter = new FilterCreaturePermanent();

    public BonfireOfTheDamnedEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals X damage to target player or planeswalker and each creature that player or that planeswalker’s controller controls";
    }

    public BonfireOfTheDamnedEffect(final BonfireOfTheDamnedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayerOrPlaneswalkerController(source.getFirstTarget());
        if (player != null) {
            int damage = source.getManaCostsToPay().getX();
            if (damage > 0) {
                player.damage(damage, source.getSourceId(), game, false, true);
                for (Permanent perm : game.getBattlefield().getAllActivePermanents(filter, player.getId(), game)) {
                    perm.damage(damage, source.getSourceId(), game, false, true);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public BonfireOfTheDamnedEffect copy() {
        return new BonfireOfTheDamnedEffect(this);
    }

}
