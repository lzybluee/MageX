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
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class PutAway extends CardImpl {

    public PutAway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}{U}");


        // Counter target spell. You may shuffle up to one target card from your graveyard into your library.
        this.getSpellAbility().addEffect(new PutAwayEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 1, new FilterCard()));

    }

    public PutAway(final PutAway card) {
        super(card);
    }

    @Override
    public PutAway copy() {
        return new PutAway(this);
    }
}

class PutAwayEffect extends OneShotEffect {
    
    boolean countered = false;

    public PutAwayEffect() {
        super(Outcome.Neutral);
        this.staticText = "Counter target spell. You may shuffle up to one target card from your graveyard into your library";
    }

    public PutAwayEffect(final PutAwayEffect effect) {
        super(effect);
    }

    @Override
    public PutAwayEffect copy() {
        return new PutAwayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(source.getFirstTarget());
        Card card = game.getCard(source.getTargets().get(1).getFirstTarget());
        Player you = game.getPlayer(source.getControllerId());
        if (spell != null
                && game.getStack().counter(spell.getId(), source.getSourceId(), game)) {
            countered = true;
        }
        if (you != null) {
            if (card != null
                    && you.chooseUse(Outcome.Benefit, "Do you wish to shuffle up to one target card from your graveyard into your library?", source, game)
                    && game.getState().getZone(card.getId()).match(Zone.GRAVEYARD)) {
                card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, false);
                you.shuffleLibrary(source, game);
            }
        }
        return countered;
    }
}