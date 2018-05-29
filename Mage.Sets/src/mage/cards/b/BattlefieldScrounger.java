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
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.condition.common.CardsInControllerGraveCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author emerald000
 */
public final class BattlefieldScrounger extends CardImpl {

    public BattlefieldScrounger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.CENTAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Threshold - Put three cards from your graveyard on the bottom of your library: Battlefield Scrounger gets +3/+3 until end of turn. Activate this ability only once each turn, and only if seven or more cards are in your graveyard.
        Ability ability = new LimitedTimesPerTurnActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(3, 3, Duration.EndOfTurn),
                new BattlefieldScroungerCost(),
                1,
                new CardsInControllerGraveCondition(7));
        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    public BattlefieldScrounger(final BattlefieldScrounger card) {
        super(card);
    }

    @Override
    public BattlefieldScrounger copy() {
        return new BattlefieldScrounger(this);
    }
}

class BattlefieldScroungerCost extends CostImpl {

    BattlefieldScroungerCost() {
        this.addTarget(new TargetCardInYourGraveyard(3, 3, new FilterCard()));
        this.text = "Put three cards from your graveyard on the bottom of your library";
    }


    BattlefieldScroungerCost(final BattlefieldScroungerCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            if (targets.choose(Outcome.Removal, controllerId, sourceId, game)) {
                for (UUID targetId: targets.get(0).getTargets()) {
                    Card card = game.getCard(targetId);
                    if (card == null || game.getState().getZone(targetId) != Zone.GRAVEYARD) {
                        return false;
                    }
                    paid |= controller.moveCardToLibraryWithInfo(card, sourceId, game, Zone.GRAVEYARD, false, true);
                }
            }

        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return targets.canChoose(controllerId, game);
    }

    @Override
    public BattlefieldScroungerCost copy() {
        return new BattlefieldScroungerCost(this);
    }
}
