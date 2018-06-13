
package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BecomesBasicLandTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class StreambedAquitects extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Merfolk creature");
    static {
        filter.add(new SubtypePredicate(SubType.MERFOLK));
    }

    public StreambedAquitects(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {tap}: Target Merfolk creature gets +1/+1 and gains islandwalk until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(1,1, Duration.EndOfTurn), new TapSourceCost());
        ability.addEffect(new GainAbilityTargetEffect(new IslandwalkAbility(false), Duration.EndOfTurn));
        Target target = new TargetCreaturePermanent(filter);
        ability.addTarget(target);
        this.addAbility(ability);

        // {tap}: Target land becomes an Island until end of turn.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesBasicLandTargetEffect(Duration.EndOfTurn, SubType.ISLAND), new TapSourceCost());
        target = new TargetLandPermanent();
        ability.addTarget(target);
        this.addAbility(ability);
    }

    public StreambedAquitects(final StreambedAquitects card) {
        super(card);
    }

    @Override
    public StreambedAquitects copy() {
        return new StreambedAquitects(this);
    }
}
