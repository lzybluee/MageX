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
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author LevelX2
 */
public final class RotfeasterMaggot extends CardImpl {

    public RotfeasterMaggot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // When Rotfeaster Maggot enters the battlefield, exile target creature card from a graveyard. You gain life equal to that card's toughness.
        Ability ability = new EntersBattlefieldTriggeredAbility(new RotfeasterMaggotExileEffect(), false);
        ability.addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card from a graveyard")));
        this.addAbility(ability);
    }

    public RotfeasterMaggot(final RotfeasterMaggot card) {
        super(card);
    }

    @Override
    public RotfeasterMaggot copy() {
        return new RotfeasterMaggot(this);
    }
}

class RotfeasterMaggotExileEffect extends OneShotEffect {

    public RotfeasterMaggotExileEffect() {
        super(Outcome.GainLife);
        this.staticText = "exile target creature card from a graveyard. You gain life equal to that card's toughness";
    }

    public RotfeasterMaggotExileEffect(final RotfeasterMaggotExileEffect effect) {
        super(effect);
    }

    @Override
    public RotfeasterMaggotExileEffect copy() {
        return new RotfeasterMaggotExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card targetCard = game.getCard(getTargetPointer().getFirst(game, source));
            if (targetCard != null) {
                if (game.getState().getZone(targetCard.getId()) == Zone.GRAVEYARD) {
                    controller.moveCardToExileWithInfo(targetCard, null, "", source.getSourceId(), game, Zone.GRAVEYARD, true);
                }
                controller.gainLife(targetCard.getToughness().getValue(), game, source);
                return true;
            }
        }
        return false;
    }
}
