
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LoneFox
 */
public final class AnimateLand extends CardImpl {

    public AnimateLand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");

        // Until end of turn, target land becomes a 3/3 creature that's still a land.
        this.getSpellAbility().addEffect(new BecomesCreatureTargetEffect(new CreatureToken(3, 3), false, true, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetLandPermanent());
    }

    public AnimateLand(final AnimateLand card) {
        super(card);
    }

    @Override
    public AnimateLand copy() {
        return new AnimateLand(this);
    }
}