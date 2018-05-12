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
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SpliceOntoArcaneAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterPermanentCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Library;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class Reweave extends CardImpl {

    public Reweave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{5}{U}");
        this.subtype.add(SubType.ARCANE);

        // Target permanent's controller sacrifices it. If he or she does, that player reveals cards from the top of their library until he or she reveals a permanent card that shares a card type with the sacrificed permanent, puts that card onto the battlefield, then shuffles their library.
        this.getSpellAbility().addEffect(new ReweaveEffect());
        Target target = new TargetPermanent();
        this.getSpellAbility().addTarget(target);

        // Splice onto Arcane {2}{U}{U}
        this.addAbility(new SpliceOntoArcaneAbility("{2}{U}{U}"));
    }

    public Reweave(final Reweave card) {
        super(card);
    }

    @Override
    public Reweave copy() {
        return new Reweave(this);
    }
}

class ReweaveEffect extends OneShotEffect {

    private static final FilterPermanentCard filter = new FilterPermanentCard();

    public ReweaveEffect() {
        super(Outcome.Detriment);
        this.staticText = "Target permanent's controller sacrifices it. If he or she does, that player reveals cards from the top of their library until he or she reveals a permanent card that shares a card type with the sacrificed permanent, puts that card onto the battlefield, then shuffles their library";
    }

    public ReweaveEffect(final ReweaveEffect effect) {
        super(effect);
    }

    @Override
    public ReweaveEffect copy() {
        return new ReweaveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (permanent != null && sourceObject != null) {
            if (permanent.sacrifice(source.getSourceId(), game)) {
                Player permanentController = game.getPlayer(permanent.getControllerId());
                if (permanentController != null) {
                    Library library = permanentController.getLibrary();
                    if (library.hasCards()) {
                        Cards cards = new CardsImpl();
                        Card permanentCard = null;
                        for (Card card : permanentController.getLibrary().getCards(game)) {
                            cards.add(card);
                            if (card.isPermanent()) {
                                for (CardType cardType : permanent.getCardType()) {
                                    if (card.getCardType().contains(cardType)) {
                                        permanentCard = card;
                                        break;
                                    }
                                }
                            }
                        }
                        permanentController.revealCards(source, cards, game);
                        if (permanentCard != null) {
                            permanentController.moveCards(permanentCard, Zone.BATTLEFIELD, source, game);
                        }
                        permanentController.shuffleLibrary(source, game);

                    }
                    return true;
                }
                return false;
            }
        }
        return true;
    }
}
