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
package mage.cards.e;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2 & L_J
 */
public final class EssenceFilter extends CardImpl {

    public EssenceFilter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}{G}");

        // Destroy all enchantments or all nonwhite enchantments.
        this.getSpellAbility().addEffect(new EssenceFilterEffect());
    }

    public EssenceFilter(final EssenceFilter card) {
        super(card);
    }

    @Override
    public EssenceFilter copy() {
        return new EssenceFilter(this);
    }
}

class EssenceFilterEffect extends OneShotEffect {

    private static final FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent("nonwhite enchantments");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.WHITE)));
    }

    public EssenceFilterEffect() {
        super(Outcome.Detriment);
        this.staticText = "Destroy all enchantments or all nonwhite enchantments";
    }

    public EssenceFilterEffect(final EssenceFilterEffect effect) {
        super(effect);
    }

    @Override
    public EssenceFilterEffect copy() {
        return new EssenceFilterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.chooseUse(outcome, "Destroy all enchantments? (otherwise all nonwhite enchantments are destroyed)", source, game)) {
                for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterEnchantmentPermanent(), controller.getId(), source.getSourceId(), game)) {
                    permanent.destroy(source.getSourceId(), game, false);
                }
            } else {
                for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, controller.getId(), source.getSourceId(), game)) {
                    permanent.destroy(source.getSourceId(), game, false);
                }
            }
            return true;
        }
        return false;
    }
}
