
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class StrengthFromTheFallen extends CardImpl {

    public StrengthFromTheFallen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{G}");


        // Constellation - Whenever Strength from the Fallen or another entchantment enters the battlefield under your control, target creature gets +X/+X until end of turn, where X is the number of creature cards in your graveyard.
        DynamicValue xValue = new CardsInControllerGraveyardCount(new FilterCreatureCard("creature cards"));
        Ability ability = new ConstellationAbility(new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn, true));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    public StrengthFromTheFallen(final StrengthFromTheFallen card) {
        super(card);
    }

    @Override
    public StrengthFromTheFallen copy() {
        return new StrengthFromTheFallen(this);
    }
}
