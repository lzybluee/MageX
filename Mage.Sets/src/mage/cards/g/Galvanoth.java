
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

/**
 * @author North
 */
public final class Galvanoth extends CardImpl {

    public Galvanoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your upkeep, you may look at the top card of your library. If it's an instant or sorcery card, you may cast it without paying its mana cost.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new GalvanothEffect(), TargetController.YOU, true));
    }

    public Galvanoth(final Galvanoth card) {
        super(card);
    }

    @Override
    public Galvanoth copy() {
        return new Galvanoth(this);
    }
}

class GalvanothEffect extends OneShotEffect {

    public GalvanothEffect() {
        super(Outcome.PlayForFree);
        staticText = "look at the top card of your library. If it's an instant or sorcery card, you may cast it without paying its mana cost";
    }

    public GalvanothEffect(final GalvanothEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                controller.lookAtCards(source, null, new CardsImpl(card), game);
                if (card.isInstant() || card.isSorcery()) {
                    if (controller.chooseUse(Outcome.PlayForFree, "Cast " + card.getName() + " without paying its mana cost?", source, game)) {
                        controller.cast(card.getSpellAbility(), game, true, new MageObjectReference(source.getSourceObject(game), game));
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public GalvanothEffect copy() {
        return new GalvanothEffect(this);
    }
}
