
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
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
 * @author Loki (Mortivore), cbt33
 */
public final class Cantivore extends CardImpl {

    static final FilterCard filter = new FilterCard("enchantment cards");
    
    static {
        filter.add(new CardTypePredicate(CardType.ENCHANTMENT));
    }
 
    public Cantivore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.subtype.add(SubType.LHURGOYF);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
      

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // Cantivore's power and toughness are each equal to the number of enchantment cards in all graveyards.
        DynamicValue value = (new CardsInAllGraveyardsCount(filter));
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(value , Duration.EndOfGame))); 
    }

    public Cantivore(final Cantivore card) {
        super(card);
    }

    @Override
    public Cantivore copy() {
        return new Cantivore(this);
    }
}
