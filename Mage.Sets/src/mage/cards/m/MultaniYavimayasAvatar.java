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
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetControlledPermanent;

/**
 * @author jpgunter
 */
public class MultaniYavimayasAvatar extends CardImpl {

    private static final FilterControlledLandPermanent LANDS_YOU_CONTROL_FILTER = new FilterControlledLandPermanent();
    private static final FilterLandCard LAND_FILTER = new FilterLandCard();

    public MultaniYavimayasAvatar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        this.addAbility(ReachAbility.getInstance());
        this.addAbility(TrampleAbility.getInstance());

        //Multani, Yavimaya's Avatar gets +1/+1 for each land you control and each land in your graveyard.
        CardsInControllerGraveyardCount graveyardCount = new CardsInControllerGraveyardCount(LAND_FILTER);
        PermanentsOnBattlefieldCount permanentsOnBattlefieldCount = new PermanentsOnBattlefieldCount(LANDS_YOU_CONTROL_FILTER);

        DynamicValue powerToughnessValue = new AdditiveDynamicValue(graveyardCount, permanentsOnBattlefieldCount);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new BoostSourceEffect(powerToughnessValue, powerToughnessValue, Duration.WhileOnBattlefield)
                        .setText("{this} gets +1/+1 for each land you control and each land in your graveyard")
        ));

        //{1}{G}, Return two lands you control to their owner's hand: Return Multani from your graveyard to your hand.
        SimpleActivatedAbility returnToHand = new SimpleActivatedAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), new ManaCostsImpl<>("{1}{G}"));
        returnToHand.addCost(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(2, 2, LANDS_YOU_CONTROL_FILTER, true)));
        this.addAbility(returnToHand);
    }

    public MultaniYavimayasAvatar(final MultaniYavimayasAvatar card) {
        super(card);
    }

    @Override
    public MultaniYavimayasAvatar copy() {
        return new MultaniYavimayasAvatar(this);
    }
}
