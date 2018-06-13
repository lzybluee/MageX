
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class SafeHaven extends CardImpl {

    public SafeHaven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {2}, {tap}: Exile target creature you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(this.getId(),
            this.getIdName()), new ManaCostsImpl("{2}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
        // At the beginning of your upkeep, you may sacrifice Safe Haven. If you do, return each card exiled with Safe Haven to the battlefield under its owner's control.
        ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new SacrificeSourceEffect(),
            TargetController.YOU, true);
        ability.addEffect(new ReturnFromExileEffect(this.getId(), Zone.BATTLEFIELD,
            "If you do, return each card exiled with {this} to the battlefield under its owner's control"));
        this.addAbility(ability);
    }

    public SafeHaven(final SafeHaven card) {
        super(card);
    }

    @Override
    public SafeHaven copy() {
        return new SafeHaven(this);
    }
}
