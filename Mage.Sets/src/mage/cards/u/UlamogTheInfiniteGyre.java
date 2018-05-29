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
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceTriggeredAbility;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ShuffleIntoLibraryGraveOfSourceOwnerEffect;
import mage.abilities.keyword.AnnihilatorAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.TargetPermanent;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public final class UlamogTheInfiniteGyre extends CardImpl {

    public UlamogTheInfiniteGyre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{11}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDRAZI);

        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // When you cast Ulamog, the Infinite Gyre, destroy target permanent.
        Ability ability = new CastSourceTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);

        // Annihilator 4 (Whenever this creature attacks, defending player sacrifices four permanents.)
        this.addAbility(new AnnihilatorAbility(4));
        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());
        // When Ulamog is put into a graveyard from anywhere, its owner shuffles their graveyard into their library.
        this.addAbility(new PutIntoGraveFromAnywhereSourceTriggeredAbility(new ShuffleIntoLibraryGraveOfSourceOwnerEffect(), false));
    }

    public UlamogTheInfiniteGyre(final UlamogTheInfiniteGyre card) {
        super(card);
    }

    @Override
    public UlamogTheInfiniteGyre copy() {
        return new UlamogTheInfiniteGyre(this);
    }
}
