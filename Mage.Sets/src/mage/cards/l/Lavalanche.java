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
package mage.cards.l;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author jeffwadsworth
 */
public class Lavalanche extends CardImpl {

    public Lavalanche(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}{R}{G}");

        // Lavalanche deals X damage to target player and each creature he or she controls.
        this.getSpellAbility().addEffect(new LavalancheEffect(new ManacostVariableValue()));
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());

    }

    public Lavalanche(final Lavalanche card) {
        super(card);
    }

    @Override
    public Lavalanche copy() {
        return new Lavalanche(this);
    }
}

class LavalancheEffect extends OneShotEffect {

    private DynamicValue amount;

    public LavalancheEffect(DynamicValue amount) {
        super(Outcome.Damage);
        this.amount = amount;
        staticText = "{this} deals X damage to target player or planeswalker and each creature that player or that planeswalker’s controller controls";
    }

    public LavalancheEffect(final LavalancheEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public LavalancheEffect copy() {
        return new LavalancheEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayerOrPlaneswalkerController(source.getFirstTarget());
        if (targetPlayer == null) {
            return false;
        }
        targetPlayer.damage(amount.calculate(game, source, this), source.getSourceId(), game, false, true);
        FilterPermanent filter = new FilterPermanent("and each creature that player or that planeswalker’s controller controls");
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(new ControllerIdPredicate(targetPlayer.getId()));
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game);
        for (Permanent permanent : permanents) {
            permanent.damage(amount.calculate(game, source, this), source.getSourceId(), game, false, true);
        }
        return true;
    }
}
