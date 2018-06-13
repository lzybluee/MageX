
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.DealtDamageAndDiedTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;

/**
 *
 * @author fireshoes
 */
public final class GarzaZolPlagueQueen extends CardImpl {

    public GarzaZolPlagueQueen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{B}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Haste
        this.addAbility(HasteAbility.getInstance());
        
        // Whenever a creature dealt damage by Garza Zol, Plague Queen this turn dies, put a +1/+1 counter on Garza Zol.
        this.addAbility(new DealtDamageAndDiedTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));

        // Whenever Garza Zol deals combat damage to a player, you may draw a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(1), true));
    }

    public GarzaZolPlagueQueen(final GarzaZolPlagueQueen card) {
        super(card);
    }

    @Override
    public GarzaZolPlagueQueen copy() {
        return new GarzaZolPlagueQueen(this);
    }
}
