
package mage.cards.a;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainProtectionFromColorAllEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;


/**
 *
 * @author LevelX2
 */
public final class AkromasBlessing extends CardImpl {

    public AkromasBlessing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");


        // Choose a color. Creatures you control gain protection from the chosen color until end of turn.
        this.getSpellAbility().addEffect(new GainProtectionFromColorAllEffect(Duration.EndOfTurn, new FilterControlledCreaturePermanent("Creatures you control")));
        // Cycling {W}
        this.addAbility(new CyclingAbility(new ManaCostsImpl("{W}")));
    }

    public AkromasBlessing(final AkromasBlessing card) {
        super(card);
    }

    @Override
    public AkromasBlessing copy() {
        return new AkromasBlessing(this);
    }
}
