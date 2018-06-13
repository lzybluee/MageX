
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author Loki
 */
public final class NectarFaerie extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Faerie or Elf");

    static {
        filter.add(Predicates.or(new SubtypePredicate(SubType.FAERIE),new SubtypePredicate(SubType.ELF)));
    }

    public NectarFaerie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
        // {B}, {tap}: Target Faerie or Elf gains lifelink until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn), new ColoredManaCost(ColoredManaSymbol.B));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    public NectarFaerie(final NectarFaerie card) {
        super(card);
    }

    @Override
    public NectarFaerie copy() {
        return new NectarFaerie(this);
    }
}
