
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author daagar
 */
public final class CommanderEesha extends CardImpl {
    
    static final FilterCard filter = new FilterCard("creatures");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public CommanderEesha(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // protection from creatures
        this.addAbility(new ProtectionAbility(filter));
    }

    public CommanderEesha(final CommanderEesha card) {
        super(card);
    }

    @Override
    public CommanderEesha copy() {
        return new CommanderEesha(this);
    }
}
