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
import mage.MageInt;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Plopman
 */
public final class GreaterGargadon extends CardImpl {

    public GreaterGargadon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{9}{R}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(9);
        this.toughness = new MageInt(7);

        // Suspend 10-{R}
        this.addAbility(new SuspendAbility(10, new ManaCostsImpl("{R}"), this));
        // Sacrifice an artifact, creature, or land: Remove a time counter from Greater Gargadon. Activate this ability only if Greater Gargadon is suspended.
        this.addAbility(new GreaterGargadonAbility());
    }

    public GreaterGargadon(final GreaterGargadon card) {
        super(card);
    }

    @Override
    public GreaterGargadon copy() {
        return new GreaterGargadon(this);
    }
}

class GreaterGargadonAbility extends ActivatedAbilityImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifact, creature, or land");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.ARTIFACT), new CardTypePredicate(CardType.CREATURE), new CardTypePredicate(CardType.LAND)));
    }

    public GreaterGargadonAbility() {
        super(Zone.EXILED, new RemoveCounterSourceEffect(CounterType.TIME.createInstance()), new SacrificeTargetCost(new TargetControlledPermanent(filter)));
    }

    public GreaterGargadonAbility(final GreaterGargadonAbility ability) {
        super(ability);
    }

    @Override
    public GreaterGargadonAbility copy() {
        return new GreaterGargadonAbility(this);
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        Card card = game.getCard(this.getSourceId());
        if (card == null || card.getCounters(game).getCount(CounterType.TIME) == 0) {
            return ActivationStatus.getFalse();
        }
        return super.canActivate(playerId, game);
    }

    @Override
    public String getRule() {
        return super.getRule() + " Activate this ability only if Greater Gargadon is suspended.";
    }
}
