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
package mage.cards.l;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfCombatDelayedTriggeredAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class LegionsInitiative extends CardImpl {

    private static final FilterCreaturePermanent filterRedCreature = new FilterCreaturePermanent("Red creatures");
    private static final FilterCreaturePermanent filterWhiteCreature = new FilterCreaturePermanent("White creatures");

    static {
        filterRedCreature.add(new ColorPredicate(ObjectColor.RED));
        filterWhiteCreature.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public LegionsInitiative(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{R}{W}");

        // Red creatures you control get +1/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 0, Duration.WhileOnBattlefield, filterRedCreature)));

        // White creatures you control get +0/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(0, 1, Duration.WhileOnBattlefield, filterWhiteCreature)));

        // {R}{W}, Exile Legion's Initiative: Exile all creatures you control. At the beginning of the next combat, return those cards to the battlefield under their owner's control and those creatures gain haste until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LegionsInitiativeExileEffect(), new ManaCostsImpl("{R}{W}"));
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    public LegionsInitiative(final LegionsInitiative card) {
        super(card);
    }

    @Override
    public LegionsInitiative copy() {
        return new LegionsInitiative(this);
    }
}

class LegionsInitiativeExileEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("all creatures you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public LegionsInitiativeExileEffect() {
        super(Outcome.Detriment);
        staticText = "Exile all creatures you control. At the beginning of the next combat, return those cards to the battlefield under their owner's control and those creatures gain haste until end of turn";
    }

    public LegionsInitiativeExileEffect(final LegionsInitiativeExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean creatureExiled = false;
        for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            if (creature != null) {
                if (creature.moveToExile(source.getSourceId(), "Legion's Initiative", source.getSourceId(), game)) {
                    creatureExiled = true;
                }
            }
        }
        if (creatureExiled) {
            //create delayed triggered ability
            AtTheBeginOfCombatDelayedTriggeredAbility delayedAbility = new AtTheBeginOfCombatDelayedTriggeredAbility(new LegionsInitiativeReturnFromExileEffect());
            game.addDelayedTriggeredAbility(delayedAbility, source);
        }
        return true;
    }

    @Override
    public LegionsInitiativeExileEffect copy() {
        return new LegionsInitiativeExileEffect(this);
    }
}

class LegionsInitiativeReturnFromExileEffect extends OneShotEffect {

    public LegionsInitiativeReturnFromExileEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "At the beginning of the next combat, return those cards to the battlefield under their owner's control and those creatures gain haste until end of turn";
    }

    public LegionsInitiativeReturnFromExileEffect(final LegionsInitiativeReturnFromExileEffect effect) {
        super(effect);
    }

    @Override
    public LegionsInitiativeReturnFromExileEffect copy() {
        return new LegionsInitiativeReturnFromExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ExileZone exile = game.getExile().getExileZone(source.getSourceId());
        if (exile != null) {
            exile = exile.copy();
            for (UUID cardId : exile) {
                Card card = game.getCard(cardId);
                card.moveToZone(Zone.BATTLEFIELD, source.getSourceId(), game, false);
                Permanent returnedCreature = game.getPermanent(cardId);
                if (returnedCreature != null) {
                    ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
                    effect.setTargetPointer(new FixedTarget(returnedCreature.getId()));
                    game.addEffect(effect, source);
                }
            }
            game.getExile().getExileZone(source.getSourceId()).clear();
            return true;
        }
        return false;
    }

}
