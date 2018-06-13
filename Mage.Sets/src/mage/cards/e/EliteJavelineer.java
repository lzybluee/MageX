
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BlocksTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterAttackingCreature;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class EliteJavelineer extends CardImpl {

    public EliteJavelineer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        
        // Whenever Elite Javelineer blocks, it deals 1 damage to target attacking creature.
        Ability ability = new BlocksTriggeredAbility(new DamageTargetEffect(1, "it"), false);
        ability.addTarget(new TargetCreaturePermanent(new FilterAttackingCreature()));
        this.addAbility(ability);
    }

    public EliteJavelineer(final EliteJavelineer card) {
        super(card);
    }

    @Override
    public EliteJavelineer copy() {
        return new EliteJavelineer(this);
    }
}
