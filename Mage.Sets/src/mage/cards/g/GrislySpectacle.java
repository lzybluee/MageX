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
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class GrislySpectacle extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonartifact creature");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.ARTIFACT)));
    }

    public GrislySpectacle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}{B}");

        // Destroy target nonartifact creature. Its controller puts a number of cards equal to that creature's power from the top of their library into their graveyard.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new GrislySpectacleEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    public GrislySpectacle(final GrislySpectacle card) {
        super(card);
    }

    @Override
    public GrislySpectacle copy() {
        return new GrislySpectacle(this);
    }
}

class GrislySpectacleEffect extends OneShotEffect {

    public GrislySpectacleEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Its controller puts a number of cards equal to that creature's power from the top of their library into their graveyard";
    }

    public GrislySpectacleEffect(final GrislySpectacleEffect effect) {
        super(effect);
    }

    @Override
    public GrislySpectacleEffect copy() {
        return new GrislySpectacleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanentOrLKIBattlefield(this.getTargetPointer().getFirst(game, source));
        if (creature != null) {
            Player controller = game.getPlayer(creature.getControllerId());
            if (controller != null) {
                int power = creature.getPower().getValue();
                Effect effect = new PutLibraryIntoGraveTargetEffect(power);
                effect.setTargetPointer(new FixedTarget(controller.getId()));
                return effect.apply(game, source);
            }
        }
        return false;
    }
}
