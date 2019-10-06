package mage.target.common;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePlayerOrPlaneswalker;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TargetAnyTargetAmount extends TargetPermanentOrPlayerAmount {

    private static final FilterPermanentOrPlayer defaultFilter
            = new FilterCreaturePlayerOrPlaneswalker("targets");

    static {
        defaultFilter.getPermanentFilter().add(new CardTypePredicate(CardType.CREATURE));
    }

    public TargetAnyTargetAmount(int amount) {
        this(amount, 0);
    }

    public TargetAnyTargetAmount(int amount, int maxNumberOfTargets) {
        // 107.1c If a rule or ability instructs a player to choose “any number,” that player may choose
        // any positive number or zero, unless something (such as damage or counters) is being divided
        // or distributed among “any number” of players and/or objects. In that case, a nonzero number
        // of players and/or objects must be chosen if possible.
        this(new StaticValue(amount), maxNumberOfTargets);
        this.minNumberOfTargets = 1;
    }

    public TargetAnyTargetAmount(DynamicValue amount) {
        this(amount, 0);
    }

    public TargetAnyTargetAmount(DynamicValue amount, int maxNumberOfTargets) {
        super(amount, maxNumberOfTargets);
        this.zone = Zone.ALL;
        this.filter = defaultFilter;
        this.targetName = filter.getMessage();
    }

    private TargetAnyTargetAmount(final TargetAnyTargetAmount target) {
        super(target);
        this.filter = target.filter.copy();
    }

    @Override
    public TargetAnyTargetAmount copy() {
        return new TargetAnyTargetAmount(this);
    }
}
