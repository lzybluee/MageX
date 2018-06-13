
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTargetTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author LoneFox
 */
public final class BoneshardSlasher extends CardImpl {

    public BoneshardSlasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Threshold - As long as seven or more cards are in your graveyard, Boneshard Slasher gets +2/+2 and has "When Boneshard Slasher becomes the target of a spell or ability, sacrifice it."
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield), new CardsInControllerGraveCondition(7),
            "As long as seven or more cards are in your graveyard, {this} gets +2/+2"));
        Effect effect = new ConditionalContinuousEffect(new GainAbilitySourceEffect(new BecomesTargetTriggeredAbility(new SacrificeSourceEffect())),
            new CardsInControllerGraveCondition(7), "and has \"When {this} becomes the target of a spell or ability, sacrifice it.\"");
        ability.addEffect(effect);
        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    public BoneshardSlasher(final BoneshardSlasher card) {
        super(card);
    }

    @Override
    public BoneshardSlasher copy() {
        return new BoneshardSlasher(this);
    }
}
