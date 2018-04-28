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
package mage.cards.r;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.token.GoblinSoldierToken;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 *
 */
public class RiseOfTheHobgoblins extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Red creatures and white creatures");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.RED),
                new ColorPredicate(ObjectColor.WHITE)));
    }

    public RiseOfTheHobgoblins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R/W}{R/W}");

        // When Rise of the Hobgoblins enters the battlefield, you may pay {X}. If you do, create X 1/1 red and white Goblin Soldier creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new RiseOfTheHobgoblinsEffect()));

        // {RW}: Red creatures and white creatures you control gain first strike until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn, filter), new ManaCostsImpl("{R/W}")));

    }

    public RiseOfTheHobgoblins(final RiseOfTheHobgoblins card) {
        super(card);
    }

    @Override
    public RiseOfTheHobgoblins copy() {
        return new RiseOfTheHobgoblins(this);
    }
}

class RiseOfTheHobgoblinsEffect extends OneShotEffect {

    public RiseOfTheHobgoblinsEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "you may pay {X}. If you do, create X 1/1 red and white Goblin Soldier creature tokens";
    }

    public RiseOfTheHobgoblinsEffect(final RiseOfTheHobgoblinsEffect effect) {
        super(effect);
    }

    @Override
    public RiseOfTheHobgoblinsEffect copy() {
        return new RiseOfTheHobgoblinsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        ManaCosts<ManaCost> cost = new ManaCostsImpl<>("{X}");
        if (you != null && you.chooseUse(Outcome.Neutral, "Do you want to to pay {X}?", source, game)) {
            int costX = you.announceXMana(0, Integer.MAX_VALUE, "Announce the value for {X}", game, source);
            cost.add(new GenericManaCost(costX));
            if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false, null)) {
                Token token = new GoblinSoldierToken();
                return token.putOntoBattlefield(costX, game, source.getSourceId(), source.getControllerId());
            }
        }
        return false;
    }
}
