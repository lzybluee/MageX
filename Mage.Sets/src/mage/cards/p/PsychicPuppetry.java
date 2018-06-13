
package mage.cards.p;

import mage.abilities.effects.common.MayTapOrUntapTargetEffect;
import mage.abilities.keyword.SpliceOntoArcaneAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class PsychicPuppetry extends CardImpl {

    public PsychicPuppetry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");
        this.subtype.add(SubType.ARCANE);


        // You may tap or untap target permanent.
        this.getSpellAbility().addEffect(new MayTapOrUntapTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());
        // Splice onto Arcane {U}
        this.addAbility(new SpliceOntoArcaneAbility("{U}"));
    }

    public PsychicPuppetry(final PsychicPuppetry card) {
        super(card);
    }

    @Override
    public PsychicPuppetry copy() {
        return new PsychicPuppetry(this);
    }
}
