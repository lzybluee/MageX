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
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.PhaseStep;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author Loki
 */
public final class FiremaneAngel extends CardImpl {

    public FiremaneAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Firststrike
        this.addAbility(FirstStrikeAbility.getInstance());
        // At the beginning of your upkeep, if Firemane Angel is in your graveyard or on the battlefield, you may gain 1 life.
        Ability ability = new ConditionalTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(Zone.ALL, new GainLifeEffect(1), TargetController.YOU, true),
                SourceOnBattlefieldOrGraveyardCondition.instance,
                "At the beginning of your upkeep, if {this} is in your graveyard or on the battlefield, you may gain 1 life");
        this.addAbility(ability);
        // {6}{R}{R}{W}{W}: Return Firemane Angel from your graveyard to the battlefield. Activate this ability only during your upkeep.
        this.addAbility(new ConditionalActivatedAbility(Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToBattlefieldEffect(), new ManaCostsImpl("{6}{R}{R}{W}{W}"), new IsStepCondition(PhaseStep.UPKEEP), null));
    }

    public FiremaneAngel(final FiremaneAngel card) {
        super(card);
    }

    @Override
    public FiremaneAngel copy() {
        return new FiremaneAngel(this);
    }
}

enum SourceOnBattlefieldOrGraveyardCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD
                || game.getState().getZone(source.getSourceId()) == Zone.BATTLEFIELD;
    }

    @Override
    public String toString() {
        return "if {this} is in your graveyard or on the battlefield";
    }

}
