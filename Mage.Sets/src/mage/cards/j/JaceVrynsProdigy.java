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
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Gender;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.CardsInControllerGraveCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.ExileAndReturnTransformedSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class JaceVrynsProdigy extends CardImpl {

    public JaceVrynsProdigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        this.transformable = true;
        this.secondSideCardClazz = JaceTelepathUnbound.class;

        // {T}: Draw a card, then discard a card. If there are five or more cards in your graveyard, exile Jace, Vryn's Prodigy, then return him to the battefield transformed under his owner's control.
        this.addAbility(new TransformAbility());
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawDiscardControllerEffect(1, 1), new TapSourceCost());
        Effect effect = new ConditionalOneShotEffect(new ExileAndReturnTransformedSourceEffect(Gender.MALE), new CardsInControllerGraveCondition(5));
        ability.addEffect(effect);
        this.addAbility(ability);

    }

    public JaceVrynsProdigy(final JaceVrynsProdigy card) {
        super(card);
    }

    @Override
    public JaceVrynsProdigy copy() {
        return new JaceVrynsProdigy(this);
    }
}
