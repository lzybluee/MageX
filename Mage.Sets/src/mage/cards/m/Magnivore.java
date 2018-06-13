
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author LevelX2
 */
public final class Magnivore extends CardImpl {

    private static final FilterCard filter = new FilterCard("sorcery cards");
    static {
        filter.add(new CardTypePredicate(CardType.SORCERY));
    }

    public Magnivore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.LHURGOYF);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Magnivore's power and toughness are each equal to the number of sorcery cards in all graveyards.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(new CardsInAllGraveyardsCount(filter), Duration.EndOfGame)));


    }

    public Magnivore(final Magnivore card) {
        super(card);
    }

    @Override
    public Magnivore copy() {
        return new Magnivore(this);
    }
}
