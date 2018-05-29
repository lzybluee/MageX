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
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveCondition;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author cbt33
 */
public final class CabalInquisitor extends CardImpl {

    public CabalInquisitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MINION);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Threshold - {1}{B}, {T}, Exile two cards from your graveyard: Target player discards a card. Activate this ability only any time you could cast a sorcery, and only if seven or more cards are in your graveyard.
        Ability ability = new ActivateAsSorceryConditionalActivatedAbility(Zone.BATTLEFIELD, new DiscardTargetEffect(1), new ManaCostsImpl("{1}{B}"), new CardsInControllerGraveCondition(7));
        ability.addTarget(new TargetPlayer());
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(2, new FilterCard("cards from your graveyard"))));
        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    public CabalInquisitor(final CabalInquisitor card) {
        super(card);
    }

    @Override
    public CabalInquisitor copy() {
        return new CabalInquisitor(this);
    }
}

class ActivateAsSorceryConditionalActivatedAbility extends ActivatedAbilityImpl {

    private static final Effects emptyEffects = new Effects();

    public ActivateAsSorceryConditionalActivatedAbility(Zone zone, Effect effect, ManaCosts cost, Condition condition) {
        super(zone, effect, cost);
        this.condition = condition;
        timing = TimingRule.SORCERY;
    }

    public ActivateAsSorceryConditionalActivatedAbility(final ActivateAsSorceryConditionalActivatedAbility ability) {
        super(ability);
    }

    @Override
    public Effects getEffects(Game game, EffectType effectType) {
        if (!condition.apply(game, this)) {
            return emptyEffects;
        }
        return super.getEffects(game, effectType);
    }

    @Override
    public ActivateAsSorceryConditionalActivatedAbility copy() {
        return new ActivateAsSorceryConditionalActivatedAbility(this);
    }

    @Override
    public String getRule() {
        return super.getRule() + " Activate this ability only any time you could cast a sorcery, and only if seven or more cards are in your graveyard.";
    }
}
