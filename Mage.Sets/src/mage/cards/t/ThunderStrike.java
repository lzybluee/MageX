

package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class ThunderStrike extends CardImpl {

    public ThunderStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");

        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 0, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn));
    }

    public ThunderStrike(final ThunderStrike card) {
        super(card);
    }

    @Override
    public ThunderStrike copy() {
        return new ThunderStrike(this);
    }
}
