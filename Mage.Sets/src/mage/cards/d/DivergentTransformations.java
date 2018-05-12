/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.cards.d;

import java.util.*;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.UndauntedAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURES;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class DivergentTransformations extends CardImpl {

    public DivergentTransformations(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{6}{R}");

        // Undaunted
        this.addAbility(new UndauntedAbility());
        // Exile two target creatures. For each of those creatures, its controller reveals cards from the top of their library until he or she reveals a creature card, puts that card onto the battlefield, then shuffles the rest into their library.
        this.getSpellAbility().addEffect(new DivergentTransformationsEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(2, 2, FILTER_PERMANENT_CREATURES, false));

    }

    public DivergentTransformations(final DivergentTransformations card) {
        super(card);
    }

    @Override
    public DivergentTransformations copy() {
        return new DivergentTransformations(this);
    }
}

class DivergentTransformationsEffect extends OneShotEffect {

    public DivergentTransformationsEffect() {
        super(Outcome.Detriment);
        this.staticText = "Exile two target creatures. For each of those creatures, its controller reveals cards from the top of their library "
                + "until he or she reveals a creature card, puts that card onto the battlefield, then shuffles the rest into their library";
    }

    public DivergentTransformationsEffect(final DivergentTransformationsEffect effect) {
        super(effect);
    }

    @Override
    public DivergentTransformationsEffect copy() {
        return new DivergentTransformationsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            List<UUID> controllerList = new ArrayList<>();
            Set<Card> toExile = new HashSet<>();
            for (UUID targetId : getTargetPointer().getTargets(game, source)) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null) {
                    toExile.add(permanent);
                    controllerList.add(permanent.getControllerId());
                }
            }
            controller.moveCards(toExile, Zone.EXILED, source, game);
            for (UUID playerId : controllerList) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    if (player.getLibrary().hasCards()) {
                        Cards toReveal = new CardsImpl();
                        for (Card card : player.getLibrary().getCards(game)) {
                            toReveal.add(card);
                            if (card.isCreature()) {
                                player.revealCards(source, toReveal, game);
                                player.moveCards(card, Zone.BATTLEFIELD, source, game);
                                toReveal.remove(card);
                                break;
                            }
                        }
                        if (!toReveal.isEmpty()) {
                            player.shuffleLibrary(source, game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
