
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author TheElk801
 */
public final class VonaButcherOfMagan extends CardImpl {

    public VonaButcherOfMagan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // {T}, Pay 7 life: Destroy target nonland permanent. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new TapSourceCost(), MyTurnCondition.instance);
        ability.addCost(new PayLifeCost(7));
        ability.addTarget(new TargetNonlandPermanent());
        this.addAbility(ability);
    }

    public VonaButcherOfMagan(final VonaButcherOfMagan card) {
        super(card);
    }

    @Override
    public VonaButcherOfMagan copy() {
        return new VonaButcherOfMagan(this);
    }
}
