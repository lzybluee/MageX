
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author fireshoes
 */
public final class OvalchaseDaredevil extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactPermanent("an artifact");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public OvalchaseDaredevil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Whenever an artifact enters the battlefield under your control, you may return Ovalchase Daredevil from your graveyard to your hand.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.GRAVEYARD, new ReturnToHandSourceEffect(), filter, true, SetTargetPointer.NONE, null, true));
    }

    public OvalchaseDaredevil(final OvalchaseDaredevil card) {
        super(card);
    }

    @Override
    public OvalchaseDaredevil copy() {
        return new OvalchaseDaredevil(this);
    }
}
