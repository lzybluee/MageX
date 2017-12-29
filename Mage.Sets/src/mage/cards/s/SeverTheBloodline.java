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
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public class SeverTheBloodline extends CardImpl {

    public SeverTheBloodline(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}");


        // Exile target creature and all other creatures with the same name as that creature.
        this.getSpellAbility().addEffect(new SeverTheBloodlineEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // Flashback {5}{B}{B}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl("{5}{B}{B}"), TimingRule.SORCERY));
    }

    public SeverTheBloodline(final SeverTheBloodline card) {
        super(card);
    }

    @Override
    public SeverTheBloodline copy() {
        return new SeverTheBloodline(this);
    }
}

class SeverTheBloodlineEffect extends OneShotEffect {

    public SeverTheBloodlineEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile target creature and all other creatures with the same name as that creature";
    }

    public SeverTheBloodlineEffect(final SeverTheBloodlineEffect effect) {
        super(effect);
    }

    @Override
    public SeverTheBloodlineEffect copy() {
        return new SeverTheBloodlineEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent targetPermanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (controller != null && targetPermanent != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            if (targetPermanent.getName().isEmpty()) {
                filter.add(new PermanentIdPredicate(targetPermanent.getId()));  // if no name (face down creature) only the creature itself is selected
            } else {
                filter.add(new NamePredicate(targetPermanent.getName()));
            }
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                controller.moveCardToExileWithInfo(permanent, null, "", source.getSourceId(), game, Zone.BATTLEFIELD, true);
            }
            return true;
        }
        return false;
    }
}
