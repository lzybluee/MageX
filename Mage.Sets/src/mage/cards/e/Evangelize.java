
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetOpponentsChoicePermanent;

/**
 *
 * @author spjspj
 */
public final class Evangelize extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    public Evangelize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}");

        // Buyback {2}{W}{W}
        this.addAbility(new BuybackAbility("{2}{W}{W}"));

        // Gain control of target creature of an opponent's choice that he or she controls.
        GainControlTargetEffect effect = new GainControlTargetEffect(Duration.EndOfGame);
        effect.setText("Gain control of target creature of an opponent's choice he or she controls");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetOpponentsChoicePermanent(1, 1, filter, false, true));
    }

    public Evangelize(final Evangelize card) {
        super(card);
    }

    @Override
    public Evangelize copy() {
        return new Evangelize(this);
    }
}
