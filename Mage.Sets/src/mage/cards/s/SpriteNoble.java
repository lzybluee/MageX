
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author LoneFox

 */
public final class SpriteNoble extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures you control with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public SpriteNoble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        this.subtype.add(SubType.FAERIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Other creatures you control with flying get +0/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(0, 1, Duration.WhileOnBattlefield, filter, true)));
        // {tap}: Other creatures you control with flying get +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 0, Duration.EndOfTurn, filter, true),
            new TapSourceCost()));
    }

    public SpriteNoble(final SpriteNoble card) {
        super(card);
    }

    @Override
    public SpriteNoble copy() {
        return new SpriteNoble(this);
    }
}
