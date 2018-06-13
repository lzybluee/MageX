
package mage.cards.u;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.UrzaTerrainValue;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author Melkhior
 */
public final class UrzasMine extends CardImpl {
    public UrzasMine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.subtype.add(SubType.URZAS, SubType.MINE);

        // {T}: Add {C}. If you control an Urza's Power-Plant and an Urza's Tower, add {C}{C} instead.
        Ability urzaManaAbility = new DynamicManaAbility(Mana.ColorlessMana(1), new UrzaTerrainValue(2),
                "Add {C}. If you control an Urza's Power-Plant and an Urza's Tower, add {C}{C} instead");
        this.addAbility(urzaManaAbility);
    }

    public UrzasMine(final UrzasMine card) {
        super(card);
    }

    @Override
    public UrzasMine copy() {
        return new UrzasMine(this);
    }
}
