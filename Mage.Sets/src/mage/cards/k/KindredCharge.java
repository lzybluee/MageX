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
package mage.cards.k;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Saga
 */
public final class KindredCharge extends CardImpl {

    public KindredCharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}{R}");

        // Choose a creature type. For each creature you control of the chosen type, create a token that's a copy of that creature.
        // Those tokens gain haste. Exile them at the beginning of the next end step.
        this.getSpellAbility().addEffect(new ChooseCreatureTypeEffect(Outcome.Copy));
        this.getSpellAbility().addEffect(new KindredChargeEffect());
    }

    public KindredCharge(final KindredCharge card) {
        super(card);
    }

    @Override
    public KindredCharge copy() {
        return new KindredCharge(this);
    }
}

class KindredChargeEffect extends OneShotEffect {

    public KindredChargeEffect() {
        super(Outcome.Copy);
        this.staticText = "For each creature you control of the chosen type, create a token that's a copy of that creature. "
                + "Those tokens gain haste. Exile them at the beginning of the next end step";
    }

    public KindredChargeEffect(final KindredChargeEffect effect) {
        super(effect);
    }

    @Override
    public KindredChargeEffect copy() {
        return new KindredChargeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            SubType subType = ChooseCreatureTypeEffect.getChoosenCreatureType(source.getSourceId(), game);
            if (subType != null) {
                FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature you control of the chosen type");
                filter.add(new SubtypePredicate(subType));
                for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, controller.getId(), game)) {
                    if (permanent != null) {
                        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(source.getControllerId(), null, true);
                        effect.setTargetPointer(new FixedTarget(permanent, game));
                        effect.apply(game, source);
                        for (Permanent addedToken : effect.getAddedPermanent()) {
                            Effect exileEffect = new ExileTargetEffect();
                            exileEffect.setTargetPointer(new FixedTarget(addedToken, game));
                            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect), source);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
}
