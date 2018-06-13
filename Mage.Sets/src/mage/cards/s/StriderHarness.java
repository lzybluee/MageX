
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class StriderHarness extends CardImpl {

    public StriderHarness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 and has haste.
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(1)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(HasteAbility.getInstance(), AttachmentType.EQUIPMENT)));

        // Equip 1 (1: Attach to target creature you control. Equip only as a sorcery.)
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(1, 1)));
    }

    public StriderHarness(final StriderHarness card) {
        super(card);
    }

    @Override
    public StriderHarness copy() {
        return new StriderHarness(this);
    }

}
