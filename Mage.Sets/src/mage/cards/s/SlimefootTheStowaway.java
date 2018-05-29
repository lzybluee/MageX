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
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.SaprolingToken;

/**
 *
 * @author TheElk801
 */
public final class SlimefootTheStowaway extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Saproling you control");

    static {
        filter.add(new SubtypePredicate(SubType.SAPROLING));
    }

    public SlimefootTheStowaway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.FUNGUS);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever a Saproling you control dies, Slimefoot, the Stowaway deals 1 damage to each opponent and you gain 1 life.
        Ability ability = new DiesCreatureTriggeredAbility(new DamagePlayersEffect(1, TargetController.OPPONENT), false, filter);
        ability.addEffect(new GainLifeEffect(1));
        this.addAbility(ability);

        // {4}: Create a 1/1 green Saproling creature token.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new SaprolingToken()), new ManaCostsImpl("{4}")));
    }

    public SlimefootTheStowaway(final SlimefootTheStowaway card) {
        super(card);
    }

    @Override
    public SlimefootTheStowaway copy() {
        return new SlimefootTheStowaway(this);
    }
}
