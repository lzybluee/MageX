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
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Loki
 */
public final class MoltenTailMasticore extends CardImpl {

    public MoltenTailMasticore (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.MASTICORE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, sacrifice Molten-Tail Masticore unless you discard a card.
        this.addAbility(new MoltenTailMasticoreAbility());
        // {4}, Exile a creature card from your graveyard: Molten-Tail Masticore deals 4 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(4), new GenericManaCost(4));
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(new FilterCreatureCard("a creature card from your graveyard"))));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
        // {2}: Regenerate Molten-Tail Masticore.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new GenericManaCost(2)));
    }

    public MoltenTailMasticore (final MoltenTailMasticore card) {
        super(card);
    }

    @Override
    public MoltenTailMasticore copy() {
        return new MoltenTailMasticore(this);
    }

}

class MoltenTailMasticoreAbility extends TriggeredAbilityImpl {
    public MoltenTailMasticoreAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceUnlessPaysEffect(new DiscardTargetCost(new TargetCardInHand())));
    }

    public MoltenTailMasticoreAbility(final MoltenTailMasticoreAbility ability) {
        super(ability);
    }

    @Override
    public MoltenTailMasticoreAbility copy() {
        return new MoltenTailMasticoreAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.controllerId);
    }

    @Override
    public String getRule() {
        return "At the beginning of your upkeep, sacrifice {this} unless you discard a card.";
    }
}
