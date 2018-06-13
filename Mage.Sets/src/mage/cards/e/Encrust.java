
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CantActivateAbilitiesAttachedEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class Encrust extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact or creature");

    static {
        filter.add(Predicates.or(
        new CardTypePredicate(CardType.CREATURE),
        new CardTypePredicate(CardType.ARTIFACT)));
    }

    public Encrust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}{U}");
        this.subtype.add(SubType.AURA);


        // Enchant artifact or creature
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted permanent doesn't untap during its controller's untap step and its activated abilities can't be activated.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepEnchantedEffect("permanent"));
        Effect effect = new CantActivateAbilitiesAttachedEffect();
        effect.setText("and its activated abilities can't be activated");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public Encrust(final Encrust card) {
        super(card);
    }

    @Override
    public Encrust copy() {
        return new Encrust(this);
    }
}
