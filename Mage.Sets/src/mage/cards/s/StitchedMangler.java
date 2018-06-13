
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class StitchedMangler extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public StitchedMangler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Stitched Mangler enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Stitched Mangler enters the battlefield, tap target creature an opponent controls. That creature doesn't untap during its controller's next untap step.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect());
        Effect effect = new DontUntapInControllersNextUntapStepTargetEffect();
        effect.setText("That creature doesn't untap during its controller's next untap step");
        ability.addEffect(effect);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    public StitchedMangler(final StitchedMangler card) {
        super(card);
    }

    @Override
    public StitchedMangler copy() {
        return new StitchedMangler(this);
    }
}
