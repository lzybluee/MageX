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

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.RemoveCounterTargetEffect;
import mage.abilities.keyword.RepairAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.other.CounterCardPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author Styxo
 */
public class MaintenanceDroid extends CardImpl {

    private static final FilterCard filter = new FilterCard("target card you own with a repair counter on it");

    static {
        filter.add(new CounterCardPredicate(CounterType.REPAIR));
    }

    public MaintenanceDroid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{W}{U}");
        this.subtype.add(SubType.DROID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {T}: Choose target card you own with a repair counter on it. You may remove a repair counter from it or put another repair counter on it.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MaintenanceDroidEffect(), new TapSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        // Repair 4
        this.addAbility(new RepairAbility(4));
    }

    public MaintenanceDroid(final MaintenanceDroid card) {
        super(card);
    }

    @Override
    public MaintenanceDroid copy() {
        return new MaintenanceDroid(this);
    }
}

class MaintenanceDroidEffect extends OneShotEffect {

    private static final Set<String> choices = new HashSet<>();

    static {
        choices.add("Remove a repair counter");
        choices.add("Add another repair counter");
    }

    public MaintenanceDroidEffect() {
        super(Outcome.BoostCreature);
        staticText = "Choose target card you own with a repair counter on it. You may remove a repair counter from it or put another repair counter on it";
    }

    public MaintenanceDroidEffect(final MaintenanceDroidEffect effect) {
        super(effect);
    }

    @Override
    public MaintenanceDroidEffect copy() {
        return new MaintenanceDroidEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Choice choice = new ChoiceImpl(true);
            choice.setMessage("Choose mode");
            choice.setChoices(choices);
            while (!controller.choose(outcome, choice, game)) {
                if (!controller.canRespond()) {
                    return false;
                }
            }

            String chosen = choice.getChoice();
            switch (chosen) {
                case "Remove a repair counter":
                    new RemoveCounterTargetEffect(CounterType.REPAIR.createInstance()).apply(game, source);
                    break;
                default: //"Add another repair counter"
                    new AddCountersTargetEffect(CounterType.REPAIR.createInstance()).apply(game, source);
                    break;
            }
            return true;

        }

        return false;
    }

}
