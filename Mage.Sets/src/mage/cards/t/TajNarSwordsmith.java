
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author emerald000
 */
public final class TajNarSwordsmith extends CardImpl {

    public TajNarSwordsmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Taj-Nar Swordsmith enters the battlefield, you may pay {X}. If you do, search your library for an Equipment card with converted mana cost X or less and put that card onto the battlefield. Then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TajNarSwordsmithEffect()));
    }

    public TajNarSwordsmith(final TajNarSwordsmith card) {
        super(card);
    }

    @Override
    public TajNarSwordsmith copy() {
        return new TajNarSwordsmith(this);
    }
}

class TajNarSwordsmithEffect extends OneShotEffect {
    
    TajNarSwordsmithEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may pay {X}. If you do, search your library for an Equipment card with converted mana cost X or less and put that card onto the battlefield. Then shuffle your library";
    }
    
    TajNarSwordsmithEffect(final TajNarSwordsmithEffect effect) {
        super(effect);
    }
    
    @Override
    public TajNarSwordsmithEffect copy() {
        return new TajNarSwordsmithEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && player.chooseUse(Outcome.BoostCreature, "Do you want to to pay {X}?", source, game)) {
            int costX = player.announceXMana(0, Integer.MAX_VALUE, "Announce the value for {X}", game, source);
            Cost cost = new GenericManaCost(costX);
            if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false, null)) {
                FilterCard filter = new FilterCard("Equipment card with converted mana cost " + costX + " or less");
                filter.add(new SubtypePredicate(SubType.EQUIPMENT));
                filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, costX + 1));
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, 1, filter), false, true).apply(game, source);
                return true;
            }
        }
        return false;
    }
}
