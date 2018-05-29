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
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author Styxo
 */
public final class Spellshift extends CardImpl {

    public Spellshift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Counter target instant or sorcery spell.
        this.getSpellAbility().addTarget(new TargetSpell(new FilterInstantOrSorcerySpell()));
        this.getSpellAbility().addEffect(new CounterTargetEffect());

        // Its controller reveals cards from the top of their library until he or she reveals an instant or sorcery card. That player may cast that card without paying its mana cost. Then he or she shuffles their library.
        this.getSpellAbility().addEffect(new SpellshiftEffect());
    }

    public Spellshift(final Spellshift card) {
        super(card);
    }

    @Override
    public Spellshift copy() {
        return new Spellshift(this);
    }
}

class SpellshiftEffect extends OneShotEffect {

    public SpellshiftEffect() {
        super(Outcome.Detriment);
        this.staticText = "Its controller reveals cards from the top of their library until he or she reveals an instant or sorcery card. That player may cast that card without paying its mana cost. Then he or she shuffles their library";
    }

    public SpellshiftEffect(final SpellshiftEffect effect) {
        super(effect);
    }

    @Override
    public SpellshiftEffect copy() {
        return new SpellshiftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player spellController = game.getPlayer(((Spell) game.getLastKnownInformation(targetPointer.getFirst(game, source), Zone.STACK)).getControllerId());
        if (spellController != null) {
            Cards cardsToReveal = new CardsImpl();
            Card toCast = null;
            for (Card card : spellController.getLibrary().getCards(game)) {
                cardsToReveal.add(card);
                if (card.isSorcery() || card.isInstant()) {
                    toCast = card;
                    break;
                }
            }
            spellController.revealCards(source, cardsToReveal, game);
            if (toCast != null && spellController.chooseUse(outcome, "Cast " + toCast.getLogName() + " without paying its mana cost?", source, game)) {
                spellController.cast(toCast.getSpellAbility(), game, true, new MageObjectReference(source.getSourceObject(game), game));
            }
            spellController.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
