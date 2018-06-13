
package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author North
 */
public final class BlightSickle extends CardImpl {

    public BlightSickle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+0 and has wither.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(1, 0)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(WitherAbility.getInstance(), AttachmentType.EQUIPMENT)));
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2)));
    }

    public BlightSickle(final BlightSickle card) {
        super(card);
    }

    @Override
    public BlightSickle copy() {
        return new BlightSickle(this);
    }
}
