
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.permanent.token.DragonToken2;

/**
 *
 * @author jeffwadsworth
 */
public final class DragonmasterOutcast extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("land");

    static {
        filter.add(new CardTypePredicate(CardType.LAND));
    }

    public DragonmasterOutcast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of your upkeep, if you control six or more lands, create a 5/5 red Dragon creature token with flying.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new DragonToken2(), 1), TargetController.YOU, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 5), "At the beginning of your upkeep, if you control six or more lands, create a 5/5 red Dragon creature token with flying."));
    }

    public DragonmasterOutcast(final DragonmasterOutcast card) {
        super(card);
    }

    @Override
    public DragonmasterOutcast copy() {
        return new DragonmasterOutcast(this);
    }
}
