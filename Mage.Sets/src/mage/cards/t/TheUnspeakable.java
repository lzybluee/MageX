
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Loki
 */
public final class TheUnspeakable extends CardImpl {

    private static final FilterCard filter = new FilterCard("Arcane card from your graveyard");

    static {
        filter.add(new SubtypePredicate(SubType.ARCANE));
    }

    public TheUnspeakable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{U}{U}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(6);
        this.toughness = new MageInt(7);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(TrampleAbility.getInstance());
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new ReturnToHandTargetEffect(), true);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    public TheUnspeakable(final TheUnspeakable card) {
        super(card);
    }

    @Override
    public TheUnspeakable copy() {
        return new TheUnspeakable(this);
    }
}
