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
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.OwnerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author cbt33, Nantuko (Nim Deathmantle)
 */
public class DecayingSoil extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken creature");
    static{
        filter.add(new OwnerPredicate(TargetController.YOU));
        filter.add(Predicates.not(new TokenPredicate()));
    }

    public DecayingSoil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}{B}");


        // At the beginning of your upkeep, exile a card from your graveyard.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new ExileTargetEffect(), TargetController.YOU, false);
        TargetCardInYourGraveyard target = new TargetCardInYourGraveyard();
        ability.addTarget(target);
        this.addAbility(ability);

        // Threshold - As long as seven or more cards are in your graveyard, Decaying Soil has "Whenever a nontoken creature is put into your graveyard from the battlefield, you may pay {1}. If you do, return that card to your hand."
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD,
            new ConditionalContinuousEffect(new GainAbilitySourceEffect(new DecayingSoilTriggeredAbility(new DecayingSoilEffect(), filter)),
            new CardsInControllerGraveCondition(7),
            "As long as seven or more cards are in your graveyard, {this} has \"Whenever a nontoken creature is put into your graveyard from the battlefield, you may pay {1}. If you do, return that card to your hand.\""));
        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    public DecayingSoil(final DecayingSoil card) {
        super(card);
    }

    @Override
    public DecayingSoil copy() {
        return new DecayingSoil(this);
    }
}

class DecayingSoilTriggeredAbility extends TriggeredAbilityImpl {

    protected FilterCreaturePermanent filter;

    public DecayingSoilTriggeredAbility(Effect effect, FilterCreaturePermanent filter) {
        super(Zone.BATTLEFIELD, effect, false);
        this.filter = filter;
    }

    public DecayingSoilTriggeredAbility(DecayingSoilTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
    }

    @Override
    public DecayingSoilTriggeredAbility copy() {
        return new DecayingSoilTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getFromZone() == Zone.BATTLEFIELD && zEvent.getToZone() == Zone.GRAVEYARD) {
            Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (permanent != null && filter.match(permanent, this.getSourceId(), this.getControllerId(), game)) {
                getEffects().get(0).setTargetPointer(new FixedTarget(permanent.getId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Player controller = game.getPlayer(this.getControllerId());
        return controller != null && controller.getGraveyard().contains(this.getSourceId());
    }



    @Override
    public String getRule() {
        return new StringBuilder("Whenever a ").append(filter.getMessage()).append(" is put into your graveyard from the battlefield, ").append(super.getRule()).toString();
    }
}


class DecayingSoilEffect extends OneShotEffect {

    private final Cost cost = new GenericManaCost(1);

    public DecayingSoilEffect() {
        super(Outcome.Benefit);
        staticText = "you may pay {1}. If you do, return that card to your hand";

    }

    public DecayingSoilEffect(final DecayingSoilEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (player.chooseUse(Outcome.Benefit, " - Pay " + cost.getText() + '?', source, game)) {
                cost.clearPaid();
                if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false, null)) {
                    UUID target = this.getTargetPointer().getFirst(game, source);
                      if (target != null) {
                        Card card = game.getCard(target);
                        // check if it's still in graveyard
                        if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
                            card.moveToZone(Zone.HAND, source.getSourceId(), game, true);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public DecayingSoilEffect copy() {
        return new DecayingSoilEffect(this);
    }

}
