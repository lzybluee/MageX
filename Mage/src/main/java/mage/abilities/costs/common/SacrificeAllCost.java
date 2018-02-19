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
package mage.abilities.costs.common;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.constants.AbilityType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class SacrificeAllCost extends CostImpl {

    private final FilterPermanent filter;
    private final List<Permanent> permanents = new ArrayList<>();

    public SacrificeAllCost(FilterPermanent filter) {
        this.filter = filter;
        this.text = "Sacrifice all " + filter.getMessage();
    }

    public SacrificeAllCost(final SacrificeAllCost cost) {
        super(cost);
        this.permanents.addAll(cost.permanents); // because this are already copied permanents, they can't change, so no copy again is needed
        this.filter = cost.filter.copy();
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, controllerId, game)) {
            if (permanent.sacrifice(sourceId, game)) {
                permanents.add(permanent.copy());
            }
        }
        paid = true;
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        UUID activator = controllerId;
        if (ability.getAbilityType() == AbilityType.ACTIVATED || ability.getAbilityType() == AbilityType.SPECIAL_ACTION) {
            if (((ActivatedAbilityImpl) ability).getActivatorId() != null) {
                activator = ((ActivatedAbilityImpl) ability).getActivatorId();
            } else {
                // Aktivator not filled?
                activator = controllerId;
            }
        }

        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, controllerId, game)) {
            if (!game.getPlayer(activator).canPaySacrificeCost(permanent, sourceId, controllerId, game)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public SacrificeAllCost copy() {
        return new SacrificeAllCost(this);
    }

    public List<Permanent> getPermanents() {
        return permanents;
    }

}
