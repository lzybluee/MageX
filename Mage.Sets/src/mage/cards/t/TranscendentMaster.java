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
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LevelUpAbility;
import mage.abilities.keyword.LevelerCardBuilder;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardSetInfo;
import mage.cards.LevelerCard;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author North, noxx
 */
public final class TranscendentMaster extends LevelerCard {

    public TranscendentMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.subtype.add(SubType.AVATAR);

        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(new LevelUpAbility(new ManaCostsImpl("{1}")));

        // Level up {1} ({1}: Put a level counter on this. Level up only as a sorcery.)
        // LEVEL 6-11
        // 6/6
        // Lifelink
        Abilities<Ability> abilities1 = new AbilitiesImpl<>(LifelinkAbility.getInstance());
        // LEVEL 12+
        // 9/9
        // Lifelink
        // Transcendent Master is indestructible.
        Abilities<Ability> abilities2 = new AbilitiesImpl<>(
                LifelinkAbility.getInstance(),
                IndestructibleAbility.getInstance());

        this.addAbilities(LevelerCardBuilder.construct(
                new LevelerCardBuilder.LevelAbility(6, 11, abilities1, 6, 6),
                new LevelerCardBuilder.LevelAbility(12, -1, abilities2, 9, 9)
        ));
        setMaxLevelCounters(12);
    }

    public TranscendentMaster(final TranscendentMaster card) {
        super(card);
    }

    @Override
    public TranscendentMaster copy() {
        return new TranscendentMaster(this);
    }
}
