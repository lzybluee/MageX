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
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author emerald000
 */
public class MagusOfTheUnseen extends CardImpl {
    
    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("artifact an opponent controls");
    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public MagusOfTheUnseen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{U}, {tap}: Untap target artifact an opponent controls and gain control of it until end of turn. It gains haste until end of turn. When you lose control of the artifact, tap it.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapTargetEffect(), new ManaCostsImpl<>("{1}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetArtifactPermanent(filter));
        Effect effect = new GainControlTargetEffect(Duration.EndOfTurn);
        effect.setText("and gain control of it until end of turn. ");
        ability.addEffect(effect);
        effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("It gains haste until end of turn. ");
        ability.addEffect(effect);
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new MagusOfTheUnseenDelayedTriggeredAbility(), true));
        this.addAbility(ability);
    }

    public MagusOfTheUnseen(final MagusOfTheUnseen card) {
        super(card);
    }

    @Override
    public MagusOfTheUnseen copy() {
        return new MagusOfTheUnseen(this);
    }
}

class MagusOfTheUnseenDelayedTriggeredAbility extends DelayedTriggeredAbility {

    MagusOfTheUnseenDelayedTriggeredAbility() {
        super(new TapTargetEffect(), Duration.EndOfGame, true); // effect can last over turns end, if you still control the target but only one time
    }

    MagusOfTheUnseenDelayedTriggeredAbility(MagusOfTheUnseenDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.LOST_CONTROL;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(controllerId) 
                && event.getSourceId().equals(this.getEffects().get(0).getTargetPointer().getFirst(game, this));
    }
    
    @Override
    public MagusOfTheUnseenDelayedTriggeredAbility copy() {
        return new MagusOfTheUnseenDelayedTriggeredAbility(this);
    }
    
    @Override
    public String getRule() {
        return "When you lose control of the artifact, tap it";
    }
}
