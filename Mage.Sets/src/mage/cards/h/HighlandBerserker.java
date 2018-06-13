
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author North
 */
public final class HighlandBerserker extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Ally creatures you control");

    static {
        filter.add(new SubtypePredicate(SubType.ALLY));
        filter.add(new ControllerPredicate(TargetController.YOU));
   }

    public HighlandBerserker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);
        this.subtype.add(SubType.ALLY);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Highland Berserker or another Ally enters the battlefield under your control, you may have Ally creatures you control gain first strike until end of turn.
        Effect effect = new GainAbilityAllEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn, filter);
        effect.setText("you may have Ally creatures you control gain first strike until end of turn");
        this.addAbility(new AllyEntersBattlefieldTriggeredAbility(effect, true));
    }

    public HighlandBerserker(final HighlandBerserker card) {
        super(card);
    }

    @Override
    public HighlandBerserker copy() {
        return new HighlandBerserker(this);
    }
}
