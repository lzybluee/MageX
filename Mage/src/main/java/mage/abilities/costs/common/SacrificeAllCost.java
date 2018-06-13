
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
