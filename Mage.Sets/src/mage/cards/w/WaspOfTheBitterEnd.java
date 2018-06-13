
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class WaspOfTheBitterEnd extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a Bolas planeswalker spell");

    static {
        filter.add(new CardTypePredicate(CardType.PLANESWALKER));
        filter.add(new SubtypePredicate(SubType.BOLAS));
    }

    public WaspOfTheBitterEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a Bolas planeswalker spell, you may sacrifice Wasp of the Bitter End. If you do, destroy target creature.
        Ability ability = new SpellCastControllerTriggeredAbility(new SacrificeSourceEffect(), filter, true,
                "Whenever you cast a Bolas planeswalker spell, you may sacrifice {this}. If you do, destroy target creature.");
        ability.addEffect(new DestroyTargetEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public WaspOfTheBitterEnd(final WaspOfTheBitterEnd card) {
        super(card);
    }

    @Override
    public WaspOfTheBitterEnd copy() {
        return new WaspOfTheBitterEnd(this);
    }
}
