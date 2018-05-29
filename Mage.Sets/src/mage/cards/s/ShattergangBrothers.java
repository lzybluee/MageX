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
package mage.cards.s;

import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class ShattergangBrothers extends CardImpl {

    public ShattergangBrothers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ARTIFICER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}{B}, Sacrifice a creature: Each other player sacrifices a creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShattergangBrothersEffect(FILTER_CONTROLLED_CREATURE_SHORT_TEXT), new ManaCostsImpl("{2}{B}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        this.addAbility(ability);
        // {2}{R}, Sacrifice an artifact: Each other player sacrifices an artifact.
        FilterControlledPermanent filter = new FilterControlledArtifactPermanent("an artifact");
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShattergangBrothersEffect(filter), new ManaCostsImpl("{2}{R}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(1, 1, filter, true)));
        this.addAbility(ability);
        // {2}{G}, Sacrifice an enchantment: Each other player sacrifices an enchantment.
        filter = new FilterControlledPermanent("an enchantment");
        filter.add(new CardTypePredicate(CardType.ENCHANTMENT));
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShattergangBrothersEffect(filter), new ManaCostsImpl("{2}{G}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(1, 1, filter, true)));
        this.addAbility(ability);
    }

    public ShattergangBrothers(final ShattergangBrothers card) {
        super(card);
    }

    @Override
    public ShattergangBrothers copy() {
        return new ShattergangBrothers(this);
    }
}

class ShattergangBrothersEffect extends OneShotEffect {

    private FilterControlledPermanent filter;

    public ShattergangBrothersEffect(FilterControlledPermanent filter) {
        super(Outcome.Sacrifice);
        this.filter = filter;
        this.staticText = new StringBuilder("Each other player sacrifices ").append(filter.getMessage()).toString();
    }

    public ShattergangBrothersEffect(final ShattergangBrothersEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public ShattergangBrothersEffect copy() {
        return new ShattergangBrothersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                if (!Objects.equals(playerId, source.getControllerId())) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        TargetControlledPermanent target = new TargetControlledPermanent(filter);
                        target.setNotTarget(true);
                        if (target.canChoose(source.getSourceId(), playerId, game)
                                && player.chooseTarget(outcome, target, source, game)) {
                            Permanent permanent = game.getPermanent(target.getFirstTarget());
                            if (permanent != null) {
                                permanent.sacrifice(source.getSourceId(), game);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
