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
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author jeffwadsworth
 */
public final class MindclawShaman extends CardImpl {

    public MindclawShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");
        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Mindclaw Shaman enters the battlefield, target opponent reveals their hand. You may cast an instant or sorcery card from it without paying its mana cost.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MindclawShamanEffect(), false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public MindclawShaman(final MindclawShaman card) {
        super(card);
    }

    @Override
    public MindclawShaman copy() {
        return new MindclawShaman(this);
    }
}

class MindclawShamanEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("instant or sorcery card");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    public MindclawShamanEffect() {
        super(Outcome.Discard);
        this.staticText = "target opponent reveals their hand. You may cast an instant or sorcery card from it without paying its mana cost";
    }

    public MindclawShamanEffect(final MindclawShamanEffect effect) {
        super(effect);
    }

    @Override
    public MindclawShamanEffect copy() {
        return new MindclawShamanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetOpponent = game.getPlayer(source.getFirstTarget());
        MageObject sourceObject = source.getSourceObject(game);
        if (targetOpponent != null && sourceObject != null) {
            if (!targetOpponent.getHand().isEmpty()) {
                targetOpponent.revealCards(sourceObject.getName(), targetOpponent.getHand(), game);
                Player controller = game.getPlayer(source.getControllerId());
                if (controller != null) {
                    TargetCard target = new TargetCard(Zone.HAND, filter);
                    target.setNotTarget(true);
                    if (controller.choose(Outcome.Benefit, targetOpponent.getHand(), target, game)) {
                        Card chosenCard = targetOpponent.getHand().get(target.getFirstTarget(), game);
                        if (chosenCard != null) {
                            if (controller.chooseUse(Outcome.Benefit, "Cast the chosen card?", source, game)) {
                                controller.cast(chosenCard.getSpellAbility(), game, true, new MageObjectReference(source.getSourceObject(game), game));
                            } else {
                                game.informPlayers(sourceObject.getLogName() + ": " + controller.getLogName() + " canceled casting the card.");
                            }
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
