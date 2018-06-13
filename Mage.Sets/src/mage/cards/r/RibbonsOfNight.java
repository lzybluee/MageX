
package mage.cards.r;

import java.util.UUID;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Wehk
 */
public final class RibbonsOfNight extends CardImpl {

    public RibbonsOfNight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}");

        // Ribbons of Night deals 4 damage to target creature
        Effect effect = new DamageTargetEffect(4);
        this.getSpellAbility().addEffect(effect);
        
        // and you gain 4 life.
        effect = new GainLifeEffect(4);
        effect.setText("and you gain 4 life");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        
        //If {U} was spent to cast Ribbons of Night, draw a card.
       this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1), 
                new ManaWasSpentCondition(ColoredManaSymbol.U), "If {U} was spent to cast {this}, draw a card"));
    }

    public RibbonsOfNight(final RibbonsOfNight card) {
        super(card);
    }

    @Override
    public RibbonsOfNight copy() {
        return new RibbonsOfNight(this);
    }
}
