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
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LevelUpAbility;
import mage.abilities.keyword.LevelerCardBuilder;
import mage.cards.CardSetInfo;
import mage.cards.LevelerCard;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki, noxx
 */
public final class StudentOfWarfare extends LevelerCard {

    public StudentOfWarfare (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new LevelUpAbility(new ManaCostsImpl("{W}")));
        Abilities<Ability> abilities1 = new AbilitiesImpl<>();
        abilities1.add(FirstStrikeAbility.getInstance());

        Abilities<Ability> abilities2 = new AbilitiesImpl<>();
        abilities2.add(DoubleStrikeAbility.getInstance());

        this.addAbilities(LevelerCardBuilder.construct(
                new LevelerCardBuilder.LevelAbility(2, 6, abilities1, 3, 3),
                new LevelerCardBuilder.LevelAbility(7, -1, abilities2, 4, 4)
        ));
        setMaxLevelCounters(7);
    }

    public StudentOfWarfare (final StudentOfWarfare card) {
        super(card);
    }

    @Override
    public StudentOfWarfare copy() {
        return new StudentOfWarfare(this);
    }

}
