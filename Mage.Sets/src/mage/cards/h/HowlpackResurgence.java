
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author fireshoes
 */
public final class HowlpackResurgence extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you control that's a Wolf or a Werewolf");

    static {
        filter.add(Predicates.or(new SubtypePredicate(SubType.WOLF),
                new SubtypePredicate(SubType.WEREWOLF)));
    }

    public HowlpackResurgence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}");

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Each creature you control that's a Wolf or a Werewolf gets +1/+1 and has trample.
        Effect effect = new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter);
        effect.setText("Each creature you control that's Wolf or a Werewolf gets +1/+1");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter);
        effect.setText("and has trample");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public HowlpackResurgence(final HowlpackResurgence card) {
        super(card);
    }

    @Override
    public HowlpackResurgence copy() {
        return new HowlpackResurgence(this);
    }
}
