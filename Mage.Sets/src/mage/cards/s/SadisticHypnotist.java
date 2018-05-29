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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author cbt33
 */
public final class SadisticHypnotist extends CardImpl {

    public SadisticHypnotist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MINION);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Sacrifice a creature: Target player discards two cards. Activate this ability only any time you could cast a sorcery.
        TargetControlledPermanent target = new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT);
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new DiscardTargetEffect(2), new SacrificeTargetCost(target));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public SadisticHypnotist(final SadisticHypnotist card) {
        super(card);
    }

    @Override
    public SadisticHypnotist copy() {
        return new SadisticHypnotist(this);
    }
}
