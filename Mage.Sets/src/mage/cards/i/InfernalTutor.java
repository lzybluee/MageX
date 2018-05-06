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
package mage.cards.i;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public class InfernalTutor extends CardImpl {

    public InfernalTutor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Reveal a card from your hand. Search your library for a card with the same name as that card, reveal it, put it into your hand, then shuffle your library.
        this.getSpellAbility().addEffect(new InfernalTutorEffect());
        // Hellbent - If you have no cards in hand, instead search your library for a card, put it into your hand, then shuffle your library.
        Effect effect = new ConditionalOneShotEffect(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(new FilterCard()), false, true),
                HellbentCondition.instance,
                "<br/><br/><i>Hellbent</i> &mdash; If you have no cards in hand, instead search your library for a card, put it into your hand, then shuffle your library");
        this.getSpellAbility().addEffect(effect);

    }

    public InfernalTutor(final InfernalTutor card) {
        super(card);
    }

    @Override
    public InfernalTutor copy() {
        return new InfernalTutor(this);
    }
}

class InfernalTutorEffect extends OneShotEffect {

    public InfernalTutorEffect() {
        super(Outcome.Benefit);
        this.staticText = "Reveal a card from your hand. Search your library for a card with the same name as that card, reveal it, put it into your hand, then shuffle your library";
    }

    public InfernalTutorEffect(final InfernalTutorEffect effect) {
        super(effect);
    }

    @Override
    public InfernalTutorEffect copy() {
        return new InfernalTutorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            if (!controller.getHand().isEmpty()) {
                Card cardToReveal = null;
                if (controller.getHand().size() > 1) {
                    Target target = new TargetCardInHand(new FilterCard());
                    target.setNotTarget(true);
                    if (controller.chooseTarget(outcome, target, source, game)) {
                        cardToReveal = game.getCard(target.getFirstTarget());
                    }
                } else {
                    cardToReveal = controller.getHand().getRandom(game);
                }
                FilterCard filterCard;
                if (cardToReveal != null) {
                    controller.revealCards("from hand :" + sourceObject.getName(), new CardsImpl(cardToReveal), game);
                    String nameToSearch = cardToReveal.isSplitCard() ? ((SplitCard) cardToReveal).getLeftHalfCard().getName() : cardToReveal.getName();
                    filterCard = new FilterCard("card named " + nameToSearch);
                    filterCard.add(new NamePredicate(nameToSearch));
                } else {
                    filterCard = new FilterCard();
                }
                return new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filterCard), true, true).apply(game, source);

            }
            return true;
        }
        return false;
    }
}
