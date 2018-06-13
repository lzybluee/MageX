

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Backfir3
 */
public final class StrongarmThug extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("Mercenary card");

    static {
        filter.add(new SubtypePredicate(SubType.MERCENARY));
    }

    public StrongarmThug(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Strongarm Thug enters the battlefield, you may return target Mercenary card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect(), true);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    public StrongarmThug(final StrongarmThug card) {
        super(card);
    }

    @Override
    public StrongarmThug copy() {
        return new StrongarmThug(this);
    }
}
