package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author TheElk801
 */
public final class ViashinoPyromancer extends CardImpl {

    public ViashinoPyromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Viashino Pyromancer enters the battlefield, it deals 2 damage to target player or planeswalker.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(2));
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    public ViashinoPyromancer(final ViashinoPyromancer card) {
        super(card);
    }

    @Override
    public ViashinoPyromancer copy() {
        return new ViashinoPyromancer(this);
    }
}
