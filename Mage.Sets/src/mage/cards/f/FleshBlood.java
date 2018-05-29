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
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCreaturePermanent;

public final class FleshBlood extends SplitCard {

    public FleshBlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{G}", "{R}{G}", SpellAbilityType.SPLIT_FUSED);

        // Flesh
        // Exile target creature card from a graveyard. Put X +1/+1 counters on target creature, where X is the power of the card you exiled.
        Target target = new TargetCardInGraveyard(new FilterCreatureCard());
        getLeftHalfCard().getSpellAbility().addTarget(target);
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
        getLeftHalfCard().getSpellAbility().addEffect(new FleshEffect());

        // Blood
        // Target creature you control deals damage equal to its power to any target.
        getRightHalfCard().getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        getRightHalfCard().getSpellAbility().addTarget(new TargetAnyTarget());
        getRightHalfCard().getSpellAbility().addEffect(new BloodEffect());

    }

    public FleshBlood(final FleshBlood card) {
        super(card);
    }

    @Override
    public FleshBlood copy() {
        return new FleshBlood(this);
    }
}

class FleshEffect extends OneShotEffect {

    public FleshEffect() {
        super(Outcome.BoostCreature);
        staticText = "Exile target creature card from a graveyard. Put X +1/+1 counters on target creature, where X is the power of the card you exiled";
    }

    public FleshEffect(final FleshEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card targetCard = game.getCard(source.getFirstTarget());
        if (targetCard != null) {
            int power = targetCard.getPower().getValue();
            targetCard.moveToExile(null, null, source.getSourceId(), game);
            if (power > 0) {
                Permanent targetCreature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
                if (targetCreature != null) {
                    targetCreature.addCounters(CounterType.P1P1.createInstance(power), source, game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public FleshEffect copy() {
        return new FleshEffect(this);
    }

}

class BloodEffect extends OneShotEffect {

    public BloodEffect() {
        super(Outcome.Damage);
        staticText = "Target creature you control deals damage equal to its power to any target";
    }

    public BloodEffect(final BloodEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getFirstTarget());
        if (sourcePermanent == null) {
            sourcePermanent = (Permanent) game.getLastKnownInformation(source.getFirstTarget(), Zone.BATTLEFIELD);
        }

        Permanent targetCreature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (sourcePermanent != null && targetCreature != null) {
            targetCreature.damage(sourcePermanent.getPower().getValue(), sourcePermanent.getId(), game, false, true);
            return true;
        }
        Player targetPlayer = game.getPlayer(source.getTargets().get(1).getFirstTarget());
        if (sourcePermanent != null && targetPlayer != null) {
            targetPlayer.damage(sourcePermanent.getPower().getValue(), sourcePermanent.getId(), game, false, true);
            return true;
        }
        return false;
    }

    @Override
    public BloodEffect copy() {
        return new BloodEffect(this);
    }

}
