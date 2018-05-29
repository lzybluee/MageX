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
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class JaddiLifestrider extends CardImpl {

    public JaddiLifestrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(8);

        // When Jaddi Lifestrider enters the battlefield, you may tap any number of untapped creatures you control. You gain 2 life for each creature tapped this way.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new JaddiLifestriderEffect(), true));
    }

    public JaddiLifestrider(final JaddiLifestrider card) {
        super(card);
    }

    @Override
    public JaddiLifestrider copy() {
        return new JaddiLifestrider(this);
    }
}

class JaddiLifestriderEffect extends OneShotEffect {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("untapped creatures you control");
    
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public JaddiLifestriderEffect() {
        super(Outcome.GainLife);
        staticText = "you may tap any number of untapped creatures you control. You gain 2 life for each creature tapped this way";
    }

    public JaddiLifestriderEffect(JaddiLifestriderEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int tappedAmount = 0;
        Player you = game.getPlayer(source.getControllerId());
        TargetCreaturePermanent target = new TargetCreaturePermanent(0, Integer.MAX_VALUE, filter, true);
        if (target.canChoose(source.getControllerId(), game) && target.choose(Outcome.Tap, source.getControllerId(), source.getSourceId(), game)) {
            for (UUID creature : target.getTargets()) {
                if (creature != null) {
                    game.getPermanent(creature).tap(game);
                    tappedAmount++;
                }
            }
        }
        if (tappedAmount > 0) {
            you.gainLife(tappedAmount * 2, game, source);
            return true;
        }
        return false;
    }

    @Override
    public JaddiLifestriderEffect copy() {
        return new JaddiLifestriderEffect(this);
    }

}
