

package mage.cards.m;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.MyrToken;

/**
 *
 * @author Loki
 */
public final class MyrMatrix extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Myr");

       static {
           filter.add(new SubtypePredicate(SubType.MYR));
       }

        public MyrMatrix (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");
        this.addAbility(IndestructibleAbility.getInstance());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, false)));
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new MyrToken()), new GenericManaCost(5)));

    }

    public MyrMatrix (final MyrMatrix card) {
        super(card);
    }

    @Override
    public MyrMatrix copy() {
        return new MyrMatrix(this);
    }

}
