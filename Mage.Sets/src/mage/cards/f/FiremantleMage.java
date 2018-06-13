
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURES;

/**
 *
 * @author LevelX2
 */
public final class FiremantleMage extends CardImpl {

    public FiremantleMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // <i>Rally</i> — Whenver Firemantle Mage or another Ally enters the battlefield under your control, creatures you control gain menace until end of turn.
        this.addAbility(new AllyEntersBattlefieldTriggeredAbility(
                new GainAbilityControlledEffect(new MenaceAbility(), Duration.EndOfTurn, FILTER_PERMANENT_CREATURES), false));
    }

    public FiremantleMage(final FiremantleMage card) {
        super(card);
    }

    @Override
    public FiremantleMage copy() {
        return new FiremantleMage(this);
    }
}
