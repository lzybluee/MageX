
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class DreadbringerLampads extends CardImpl {

    public DreadbringerLampads(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.NYMPH);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Constellation - Whenever Dreadbringer Lampads or another enchantment enters the battlefield under your control, target creature gains intimidate until end of turn.
        Ability ability = new ConstellationAbility(new GainAbilityTargetEffect(IntimidateAbility.getInstance(), Duration.EndOfTurn), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public DreadbringerLampads(final DreadbringerLampads card) {
        super(card);
    }

    @Override
    public DreadbringerLampads copy() {
        return new DreadbringerLampads(this);
    }
}
