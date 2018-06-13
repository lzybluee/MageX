
package mage.cards.j;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetAnyTargetAmount;

/**
 *
 * @author jeffwadsworth
 */
public final class JawsOfStone extends CardImpl {
    
    static final private FilterControlledLandPermanent filter = new FilterControlledLandPermanent("mountains you control");
    
    static {
        filter.add(new SubtypePredicate(SubType.MOUNTAIN));
    }

    static final private String rule = "{this} deals X damage divided as you choose among any number of target creatures and/or players, where X is the number of Mountains you control as you cast {this}";
    
    public JawsOfStone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{R}");

        // Jaws of Stone deals X damage divided as you choose among any number of target creatures and/or players, where X is the number of Mountains you control as you cast Jaws of Stone.
        PermanentsOnBattlefieldCount mountains = new PermanentsOnBattlefieldCount(filter, null);
        Effect effect = new DamageMultiEffect(mountains);
        effect.setText(rule);
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetAnyTargetAmount(mountains));
        
    }

    public JawsOfStone(final JawsOfStone card) {
        super(card);
    }

    @Override
    public JawsOfStone copy() {
        return new JawsOfStone(this);
    }
}
