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
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author jeffwadsworth
 *
 */
public class FieryBombardment extends CardImpl {

    public FieryBombardment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // Chroma - {2}, Sacrifice a creature: Fiery Bombardment deals damage to any target equal to the number of red mana symbols in the sacrificed creature's mana cost.
        Effect effect = new FieryBombardmentEffect();
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{2}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        ability.addTarget(new TargetAnyTarget());
        ability.setAbilityWord(AbilityWord.CHROMA);
        this.addAbility(ability);

    }

    public FieryBombardment(final FieryBombardment card) {
        super(card);
    }

    @Override
    public FieryBombardment copy() {
        return new FieryBombardment(this);
    }
}

class FieryBombardmentEffect extends OneShotEffect {

    public FieryBombardmentEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals damage to any target equal to the number of red mana symbols in the sacrificed creature's mana cost.";
    }

    public FieryBombardmentEffect(final FieryBombardmentEffect effect) {
        super(effect);
    }

    @Override
    public FieryBombardmentEffect copy() {
        return new FieryBombardmentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int damage = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                for (Permanent permanent : ((SacrificeTargetCost) cost).getPermanents()) {
                    if (permanent != null) {
                        damage = permanent.getManaCost().getMana().getRed();
                    }
                }
            }
        }
        if (damage > 0) {
            Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
            if (player != null) {
                player.damage(damage, source.getSourceId(), game, false, true);
            } else {
                Permanent creature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
                if (creature != null) {
                    creature.damage(damage, source.getSourceId(), game, false, true);
                }
            }
        }
        return true;
    }
}
