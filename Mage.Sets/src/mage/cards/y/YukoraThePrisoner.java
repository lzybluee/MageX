
package mage.cards.y;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class YukoraThePrisoner extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("non-Ogre creatures");

    static {
        filter.add(Predicates.not(new SubtypePredicate(SubType.OGRE)));
    }

    public YukoraThePrisoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When Yukora, the Prisoner leaves the battlefield, sacrifice all non-Ogre creatures you control.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new YukoraThePrisonerEffect(), false));

    }

    public YukoraThePrisoner(final YukoraThePrisoner card) {
        super(card);
    }

    @Override
    public YukoraThePrisoner copy() {
        return new YukoraThePrisoner(this);
    }
}

class YukoraThePrisonerEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("non-Ogre creatures");

    static {
        filter.add(Predicates.not(new SubtypePredicate(SubType.OGRE)));
    }

    public YukoraThePrisonerEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "sacrifice all non-Ogre creatures you control";
    }

    public YukoraThePrisonerEffect(final YukoraThePrisonerEffect effect) {
        super(effect);
    }

    @Override
    public YukoraThePrisonerEffect copy() {
        return new YukoraThePrisonerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game);
        for (Permanent permanent : permanents) {
            permanent.sacrifice(source.getSourceId(), game);
        }
        return true;
    }
}
