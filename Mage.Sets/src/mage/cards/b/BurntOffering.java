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

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Topher
 */
public class BurntOffering extends CardImpl {

    public BurntOffering(UUID ownerID, CardSetInfo setInfo) {
        super(ownerID, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        //As an additional cost to cast Burnt Offering, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        //Add an amount of {B} and/or {R} equal to the sacrificed creature's converted mana cost.
        this.getSpellAbility().addEffect(new BurntOfferingEffect());
    }

    public BurntOffering(final BurntOffering card) {
        super(card);
    }

    @Override
    public BurntOffering copy() {
        return new BurntOffering(this);
    }
}

class BurntOfferingEffect extends OneShotEffect {

    public BurntOfferingEffect() {
        super(Outcome.PutManaInPool);
        this.staticText = "Add X mana in any combination of {B} and/or {R},"
                + " where X is the sacrificed creature's converted mana cost";
    }

    public BurntOfferingEffect(final BurntOfferingEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Choice manaChoice = new ChoiceImpl();
            Set<String> choices = new LinkedHashSet<>();
            choices.add("Red");
            choices.add("Black");
            manaChoice.setChoices(choices);
            manaChoice.setMessage("Select color of mana to add");

            int xValue = getCost(source);

            for (int i = 0; i < xValue; i++) {
                Mana mana = new Mana();
                if (!player.choose(Outcome.Benefit, manaChoice, game)) {
                    return false;
                }
                if (manaChoice.getChoice() == null) {  //Can happen if player leaves game
                    return false;
                }
                switch (manaChoice.getChoice()) {
                    case "Red":
                        mana.increaseRed();
                        break;
                    case "Black":
                        mana.increaseBlack();
                        break;
                }
                player.getManaPool().addMana(mana, game, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public Effect copy() {
        return new BurntOfferingEffect(this);
    }

    /**
     * Helper method to determine the CMC of the sacrificed creature.
     *
     * @param sourceAbility
     * @return
     */
    private int getCost(Ability sourceAbility) {
        for (Cost cost : sourceAbility.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                SacrificeTargetCost sacrificeCost = (SacrificeTargetCost) cost;
                int totalCMC = 0;
                for (Permanent permanent : sacrificeCost.getPermanents()) {
                    totalCMC += permanent.getConvertedManaCost();
                }
                return totalCMC;
            }
        }
        return 0;
    }
}
