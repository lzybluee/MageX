
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.TapAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author fireshoes
 */
public final class SubjugatorAngel extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures your opponents control");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public SubjugatorAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{W}");
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Subjugator Angel enters the battlefield, tap all creatures your opponents control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TapAllEffect(filter)));
    }

    public SubjugatorAngel(final SubjugatorAngel card) {
        super(card);
    }

    @Override
    public SubjugatorAngel copy() {
        return new SubjugatorAngel(this);
    }
}
