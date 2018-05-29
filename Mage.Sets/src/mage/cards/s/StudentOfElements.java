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
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;

/**
 *
 * @author LevelX2
 */
public final class StudentOfElements extends CardImpl {

    public StudentOfElements(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.flipCard = true;
        this.flipCardName = "Tobita, Master of Winds";

        // When Student of Elements has flying, flip it.
        this.addAbility(new StudentOfElementsHasFlyingAbility());
    }

    public StudentOfElements(final StudentOfElements card) {
        super(card);
    }

    @Override
    public StudentOfElements copy() {
        return new StudentOfElements(this);
    }
}

class StudentOfElementsHasFlyingAbility extends StateTriggeredAbility {

    public StudentOfElementsHasFlyingAbility() {
        super(Zone.BATTLEFIELD, new FlipSourceEffect(new TobitaMasterOfWinds()));
    }

    public StudentOfElementsHasFlyingAbility(final StudentOfElementsHasFlyingAbility ability) {
        super(ability);
    }

    @Override
    public StudentOfElementsHasFlyingAbility copy() {
        return new StudentOfElementsHasFlyingAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(getSourceId());
        if(permanent != null && permanent.getAbilities().contains(FlyingAbility.getInstance())){
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When {this} has flying, flip it.";
    }

}

class TobitaMasterOfWinds extends TokenImpl {

    TobitaMasterOfWinds() {
        super("Tobita, Master of Winds", "");
        addSuperType(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.HUMAN);
        subtype.add(SubType.WIZARD);
        power = new MageInt(3);
        toughness = new MageInt(3);

        // Creatures you control have flying.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD, new GainAbilityControlledEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield, new FilterCreaturePermanent())));
    }
    public TobitaMasterOfWinds(final TobitaMasterOfWinds token) {
        super(token);
    }

    public TobitaMasterOfWinds copy() {
        return new TobitaMasterOfWinds(this);
    }
}
