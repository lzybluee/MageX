
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author Loki
 */
public final class SorinsVengeance extends CardImpl {

    public SorinsVengeance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}{B}");

        this.getSpellAbility().addEffect(new DamageTargetEffect(10));
        this.getSpellAbility().addEffect(new GainLifeEffect(10));
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
    }

    public SorinsVengeance(final SorinsVengeance card) {
        super(card);
    }

    @Override
    public SorinsVengeance copy() {
        return new SorinsVengeance(this);
    }

}
