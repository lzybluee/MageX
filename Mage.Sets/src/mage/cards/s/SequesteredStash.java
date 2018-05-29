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
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveControllerEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class SequesteredStash extends CardImpl {

    public SequesteredStash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {R}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {4},{T}, Sacrifice Sequestered Stash: Put the top five cards of your library into your graveyard. Then you may put an artifact card from your graveyard on top of your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PutTopCardOfLibraryIntoGraveControllerEffect(5), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new SequesteredStashEffect());
        this.addAbility(ability);

    }

    public SequesteredStash(final SequesteredStash card) {
        super(card);
    }

    @Override
    public SequesteredStash copy() {
        return new SequesteredStash(this);
    }
}

class SequesteredStashEffect extends OneShotEffect {

    public SequesteredStashEffect() {
        super(Outcome.Benefit);
        this.staticText = "Then you may put an artifact card from your graveyard on top of your library";
    }

    public SequesteredStashEffect(final SequesteredStashEffect effect) {
        super(effect);
    }

    @Override
    public SequesteredStashEffect copy() {
        return new SequesteredStashEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(new FilterArtifactCard("artifact card from your graveyard"));
        target.setNotTarget(true);
        if (target.canChoose(source.getSourceId(), source.getControllerId(), game)
                && controller.chooseUse(outcome, "Put an artifact card from your graveyard to library?", source, game)
                && controller.choose(outcome, target, source.getSourceId(), game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                controller.moveCards(card, Zone.LIBRARY, source, game);
            }
        }
        return true;
    }
}
