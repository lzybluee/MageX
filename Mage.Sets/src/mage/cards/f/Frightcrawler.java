
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.combat.CantBlockSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author cbt33
 */
public final class Frightcrawler extends CardImpl {

    public Frightcrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Fear
        this.addAbility(FearAbility.getInstance());
        // Threshold - As long as seven or more cards are in your graveyard, Frightcrawler gets +2/+2 and can't block.
        Ability thresholdAbility = new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                    new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield),
                    new CardsInControllerGraveCondition(7),
                    "If seven or more cards are in your graveyard, {this} gets +2/+2 "
                ));
            thresholdAbility.addEffect(new ConditionalContinuousEffect(
                    new CantBlockSourceEffect(Duration.WhileOnBattlefield),
                    new CardsInControllerGraveCondition(7),
                    "and can't block."));
        thresholdAbility.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(thresholdAbility);
    }

    public Frightcrawler(final Frightcrawler card) {
        super(card);
    }

    @Override
    public Frightcrawler copy() {
        return new Frightcrawler(this);
    }
}
