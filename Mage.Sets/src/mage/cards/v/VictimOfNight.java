
package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North, Loki
 */
public final class VictimOfNight extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Vampire, non-Werewolf, non-Zombie creature");

    static {
        filter.add(Predicates.not(new SubtypePredicate(SubType.VAMPIRE)));
        filter.add(Predicates.not(new SubtypePredicate(SubType.WEREWOLF)));
        filter.add(Predicates.not(new SubtypePredicate(SubType.ZOMBIE)));
    }

    public VictimOfNight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}{B}");


        // Destroy target non-Vampire, non-Werewolf, non-Zombie creature.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
    }

    public VictimOfNight(final VictimOfNight card) {
        super(card);
    }

    @Override
    public VictimOfNight copy() {
        return new VictimOfNight(this);
    }
}
