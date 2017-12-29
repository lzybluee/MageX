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

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInOpponentsGraveyard;

/**
 *
 * @author LevelX2
 */
public class SepulchralPrimordial extends CardImpl {

    public SepulchralPrimordial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        //Vigilance
        this.addAbility(IntimidateAbility.getInstance());

        // When Sepulchral Primordial enters the battlefield, for each opponent, you may put up to one
        // target creature card from that player's graveyard onto the battlefield under your control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SepulchralPrimordialEffect(), false));
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof EntersBattlefieldTriggeredAbility) {
            ability.getTargets().clear();
            for (UUID opponentId : game.getOpponents(ability.getControllerId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    FilterCard filter = new FilterCreatureCard("creature card from " + opponent.getName() + "'s graveyard");
                    filter.add(new OwnerIdPredicate(opponentId));
                    TargetCardInOpponentsGraveyard target = new TargetCardInOpponentsGraveyard(0, 1, filter);
                    ability.addTarget(target);
                }
            }
        }
    }

    public SepulchralPrimordial(final SepulchralPrimordial card) {
        super(card);
    }

    @Override
    public SepulchralPrimordial copy() {
        return new SepulchralPrimordial(this);
    }
}

class SepulchralPrimordialEffect extends OneShotEffect {

    public SepulchralPrimordialEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "for each opponent, you may put up to one target creature card from that player's graveyard onto the battlefield under your control";
    }

    public SepulchralPrimordialEffect(final SepulchralPrimordialEffect effect) {
        super(effect);
    }

    @Override
    public SepulchralPrimordialEffect copy() {
        return new SepulchralPrimordialEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Card> cardsToBattlefield = new HashSet<>();
            for (Target target : source.getTargets()) {
                if (target instanceof TargetCardInOpponentsGraveyard) {
                    Card targetCard = game.getCard(target.getFirstTarget());
                    if (targetCard != null) {
                        cardsToBattlefield.add(targetCard);
                    }
                }
            }
            if (!cardsToBattlefield.isEmpty()) {
                controller.moveCards(cardsToBattlefield, Zone.BATTLEFIELD, source, game);
            }
            return true;
        }
        return false;
    }
}
