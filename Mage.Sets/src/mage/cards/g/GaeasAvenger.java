
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;


/**
 *
 * @author MarcoMarin
 */
public final class GaeasAvenger extends CardImpl {
    
    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("artifacts opponent control");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public GaeasAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Gaea's Avenger's power and toughness are each equal to 1 plus the number of artifacts your opponents control.
        
        SetPowerToughnessSourceEffect effect = new SetPowerToughnessSourceEffect(new IntPlusDynamicValue(1, new PermanentsOnBattlefieldCount(filter)), Duration.EndOfGame);
        this.addAbility(new SimpleStaticAbility(Zone.ALL, effect));
    }

    public GaeasAvenger(final GaeasAvenger card) {
        super(card);
    }

    @Override
    public GaeasAvenger copy() {
        return new GaeasAvenger(this);
    }
}

