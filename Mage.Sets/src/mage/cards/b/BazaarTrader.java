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
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author North
 */
public class BazaarTrader extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifact, creature, or land you control");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.LAND)));
    }

    public BazaarTrader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Target player gains control of target artifact, creature, or land you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BazaarTraderEffect(), new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        ability.addTarget(new TargetControlledPermanent(filter));
        this.addAbility(ability);
    }

    public BazaarTrader(final BazaarTrader card) {
        super(card);
    }

    @Override
    public BazaarTrader copy() {
        return new BazaarTrader(this);
    }
}

class BazaarTraderEffect extends ContinuousEffectImpl {

    MageObjectReference targetPermanentReference;
    
    public BazaarTraderEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.staticText = "Target player gains control of target artifact, creature, or land you control";
    }

    public BazaarTraderEffect(final BazaarTraderEffect effect) {
        super(effect);
        this.targetPermanentReference = effect.targetPermanentReference;
    }

    @Override
    public BazaarTraderEffect copy() {
        return new BazaarTraderEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        targetPermanentReference = new MageObjectReference(source.getTargets().get(1).getFirstTarget(), game);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        Permanent permanent = targetPermanentReference.getPermanent(game);
        if (player != null && permanent != null) {
            return permanent.changeControllerId(player.getId(), game);            
        } else {
            discard();
        }
        return false;
    }
}
