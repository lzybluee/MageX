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
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public class DanceOfTheSkywise extends CardImpl {

    public DanceOfTheSkywise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Until end of turn, target creature you control becomes a blue Dragon Illusion with base power and toughness 4/4, loses all abilities, and gains flying.
        Effect effect = new BecomesCreatureTargetEffect(new DragonIllusionToken(), true, false, Duration.EndOfTurn);
        effect.setText("Until end of turn, target creature you control becomes a blue Dragon Illusion with base power and toughness 4/4, loses all abilities, and gains flying.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    public DanceOfTheSkywise(final DanceOfTheSkywise card) {
        super(card);
    }

    @Override
    public DanceOfTheSkywise copy() {
        return new DanceOfTheSkywise(this);
    }
    
        private static class DragonIllusionToken extends TokenImpl {

        public DragonIllusionToken() {
            super("Dragon", "blue Dragon Illusion with base power and toughness 4/4 and with flying");
            cardType.add(CardType.CREATURE);
            color.setBlue(true);
            subtype.add(SubType.DRAGON);
            subtype.add(SubType.ILLUSION);            
            power = new MageInt(4);
            toughness = new MageInt(4);
            this.addAbility(FlyingAbility.getInstance());
        }
        public DragonIllusionToken(final DragonIllusionToken token) {
            super(token);
        }

        public DragonIllusionToken copy() {
            return new DragonIllusionToken(this);
        }

    }
}
