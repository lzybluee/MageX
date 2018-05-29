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
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class SelfInflictedWound extends CardImpl {

    public SelfInflictedWound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}");

        // Target opponent sacrifices a green or white creature. If that player does, he or she loses 2 life.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new SelfInflictedWoundEffect());        
        
    }

    public SelfInflictedWound(final SelfInflictedWound card) {
        super(card);
    }

    @Override
    public SelfInflictedWound copy() {
        return new SelfInflictedWound(this);
    }
}

class SelfInflictedWoundEffect extends OneShotEffect {

    SelfInflictedWoundEffect() {
        super(Outcome.Sacrifice);
        staticText = "Target opponent sacrifices a green or white creature. If that player does, he or she loses 2 life";
    }

    SelfInflictedWoundEffect(SelfInflictedWoundEffect effect) {
        super(effect);
    }

    @Override
    public SelfInflictedWoundEffect copy() {
        return new SelfInflictedWoundEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetOpponent = game.getPlayer(source.getTargets().getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (targetOpponent == null || controller == null) {
            return false;
        }
        FilterControlledPermanent filter = new FilterControlledPermanent("a green or white creature");
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.GREEN), new ColorPredicate(ObjectColor.WHITE)));
        TargetControlledPermanent target = new TargetControlledPermanent(1, 1, filter, true);

        if (target.canChoose(source.getSourceId(), targetOpponent.getId(), game)) {
            targetOpponent.chooseTarget(Outcome.Sacrifice, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                if (permanent.sacrifice(source.getSourceId(), game)) {
                    targetOpponent.loseLife(2, game, false);
                }                
            }
            
        }
        return true;
    }
}
