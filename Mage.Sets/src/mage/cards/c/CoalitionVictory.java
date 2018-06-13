
package mage.cards.c;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;

/**
 * @author fireshoes
 */
public final class CoalitionVictory extends CardImpl {

    static final FilterControlledLandPermanent filterPlains = new FilterControlledLandPermanent();
    static final FilterControlledLandPermanent filterIsland = new FilterControlledLandPermanent();
    static final FilterControlledLandPermanent filterSwamp = new FilterControlledLandPermanent();
    static final FilterControlledLandPermanent filterMountain = new FilterControlledLandPermanent();
    static final FilterControlledLandPermanent filterForest = new FilterControlledLandPermanent();
    static final FilterControlledCreaturePermanent filterWhite = new FilterControlledCreaturePermanent();
    static final FilterControlledCreaturePermanent filterBlue = new FilterControlledCreaturePermanent();
    static final FilterControlledCreaturePermanent filterBlack = new FilterControlledCreaturePermanent();
    static final FilterControlledCreaturePermanent filterRed = new FilterControlledCreaturePermanent();
    static final FilterControlledCreaturePermanent filterGreen = new FilterControlledCreaturePermanent();

    static {
        filterPlains.add(new SubtypePredicate(SubType.PLAINS));
        filterIsland.add(new SubtypePredicate(SubType.ISLAND));
        filterSwamp.add(new SubtypePredicate(SubType.SWAMP));
        filterMountain.add(new SubtypePredicate(SubType.MOUNTAIN));
        filterForest.add(new SubtypePredicate(SubType.FOREST));
        filterWhite.add(new ColorPredicate(ObjectColor.WHITE));
        filterBlue.add(new ColorPredicate(ObjectColor.BLUE));
        filterBlack.add(new ColorPredicate(ObjectColor.BLACK));
        filterRed.add(new ColorPredicate(ObjectColor.RED));
        filterGreen.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public CoalitionVictory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{U}{B}{R}{G}");

        // You win the game if you control a land of each basic land type and a creature of each color.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new WinGameSourceControllerEffect(),
                new CoalitionVictoryCondition(),
                "You win the game if you control a land of each basic land type and a creature of each color."));
    }

    public CoalitionVictory(final CoalitionVictory card) {
        super(card);
    }

    @Override
    public CoalitionVictory copy() {
        return new CoalitionVictory(this);
    }
}

class CoalitionVictoryCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.getBattlefield().count(CoalitionVictory.filterPlains, source.getSourceId(), source.getControllerId(), game) < 1) {
            return false;
        }
        if (game.getBattlefield().count(CoalitionVictory.filterIsland, source.getSourceId(), source.getControllerId(), game) < 1) {
            return false;
        }
        if (game.getBattlefield().count(CoalitionVictory.filterSwamp, source.getSourceId(), source.getControllerId(), game) < 1) {
            return false;
        }
        if (game.getBattlefield().count(CoalitionVictory.filterMountain, source.getSourceId(), source.getControllerId(), game) < 1) {
            return false;
        }
        if (game.getBattlefield().count(CoalitionVictory.filterForest, source.getSourceId(), source.getControllerId(), game) < 1) {
            return false;
        }
        if (game.getBattlefield().count(CoalitionVictory.filterWhite, source.getSourceId(), source.getControllerId(), game) < 1) {
            return false;
        }
        if (game.getBattlefield().count(CoalitionVictory.filterBlue, source.getSourceId(), source.getControllerId(), game) < 1) {
            return false;
        }
        if (game.getBattlefield().count(CoalitionVictory.filterBlack, source.getSourceId(), source.getControllerId(), game) < 1) {
            return false;
        }
        if (game.getBattlefield().count(CoalitionVictory.filterRed, source.getSourceId(), source.getControllerId(), game) < 1) {
            return false;
        }
        return game.getBattlefield().count(CoalitionVictory.filterGreen, source.getSourceId(), source.getControllerId(), game) >= 1;
    }
}