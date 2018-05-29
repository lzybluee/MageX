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

package mage.cards.w;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 * @author LevelX2
 */
public final class WaxmaneBaku extends CardImpl {

    public WaxmaneBaku(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        
        // Whenever you cast a Spirit or Arcane spell, you may put a ki counter on Waxmane Baku.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.KI.createInstance()), StaticFilters.SPIRIT_OR_ARCANE_CARD, true));

        // {1}, Remove X ki counters from Waxmane Baku: Tap X target creatures.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new WaxmaneBakuTapEffect(), new GenericManaCost(1));
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.KI.createInstance(1)));
        this.addAbility(ability);
    }

    public WaxmaneBaku(final WaxmaneBaku card) {
        super(card);
    }

    @Override
    public WaxmaneBaku copy() {
        return new WaxmaneBaku(this);
    }
}

class WaxmaneBakuTapEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    public WaxmaneBakuTapEffect() {
        super(Outcome.Tap);
        staticText = "Tap X target creatures";
    }

    public WaxmaneBakuTapEffect(final WaxmaneBakuTapEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int numberToTap = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof RemoveVariableCountersSourceCost) {
                numberToTap = ((RemoveVariableCountersSourceCost) cost).getAmount();
            }
        }
        TargetPermanent target = new TargetPermanent(numberToTap, filter);
        if (target.canChoose(source.getControllerId(), game) && target.choose(Outcome.Tap, source.getControllerId(), source.getSourceId(), game)) {
            if (!target.getTargets().isEmpty()) {
                List<UUID> targets = target.getTargets();
                for (UUID targetId : targets) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null) {
                        permanent.tap(game);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public WaxmaneBakuTapEffect copy() {
        return new WaxmaneBakuTapEffect(this);
    }
}
