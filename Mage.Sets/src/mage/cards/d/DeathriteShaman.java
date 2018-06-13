
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author LevelX2
 */
public final class DeathriteShaman extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant or sorcery card from a graveyard");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    public DeathriteShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B/G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);


        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {T}: Exile target land card from a graveyard. Add one mana of any color.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(), new TapSourceCost());
        ability.addEffect(new AddManaOfAnyColorEffect());
        ability.addTarget(new TargetCardInGraveyard(new FilterLandCard("land card from a graveyard")));
        this.addAbility(ability);

        // {B}, {T}: Exile target instant or sorcery card from a graveyard. Each opponent loses 2 life.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(), new ManaCostsImpl("{B}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new LoseLifeOpponentsEffect(2));
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.addAbility(ability);

        // {G}, {T}: Exile target creature card from a graveyard. You gain 2 life.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(), new ManaCostsImpl("{G}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new GainLifeEffect(2));
        ability.addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card from a graveyard")));
        this.addAbility(ability);
    }

    public DeathriteShaman(final DeathriteShaman card) {
        super(card);
    }

    @Override
    public DeathriteShaman copy() {
        return new DeathriteShaman(this);
    }
}