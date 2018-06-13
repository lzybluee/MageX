
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class RhysTheExiled extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Elf");
    private static final FilterControlledCreaturePermanent filter2 = new FilterControlledCreaturePermanent("Elf you control");
    static {
        filter.add(new SubtypePredicate(SubType.ELF));
        filter2.add(new SubtypePredicate(SubType.ELF));
    }

    public RhysTheExiled(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Rhys the Exiled attacks, you gain 1 life for each Elf you control.
        this.addAbility(new AttacksTriggeredAbility(new GainLifeEffect(new PermanentsOnBattlefieldCount(filter2, 1)), false));
        
        // {B}, Sacrifice an Elf: Regenerate Rhys the Exiled.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ColoredManaCost(ColoredManaSymbol.B));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, false)));
        this.addAbility(ability);
    }

    public RhysTheExiled(final RhysTheExiled card) {
        super(card);
    }

    @Override
    public RhysTheExiled copy() {
        return new RhysTheExiled(this);
    }
}
