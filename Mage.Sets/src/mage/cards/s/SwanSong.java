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
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.token.SwanSongBirdToken;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public class SwanSong extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("enchantment, instant or sorcery spell");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.ENCHANTMENT),
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    public SwanSong(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Counter target enchantment, instant or sorcery spell. Its controller creates a 2/2 blue Bird creature token with flying.
        this.getSpellAbility().addEffect(new SwanSongEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    public SwanSong(final SwanSong card) {
        super(card);
    }

    @Override
    public SwanSong copy() {
        return new SwanSong(this);
    }
}

class SwanSongEffect extends OneShotEffect {

    public SwanSongEffect() {
        super(Outcome.Benefit);
        this.staticText = "Counter target enchantment, instant or sorcery spell. Its controller creates a 2/2 blue Bird creature token with flying";
    }

    public SwanSongEffect(final SwanSongEffect effect) {
        super(effect);
    }

    @Override
    public SwanSongEffect copy() {
        return new SwanSongEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean countered = false;
        for (UUID targetId : targetPointer.getTargets(game, source)) {
            Spell spell = game.getStack().getSpell(targetId);
            if (game.getStack().counter(targetId, source.getSourceId(), game)) {
                countered = true;
            }
            if (spell != null) {
                Token token = new SwanSongBirdToken();
                token.putOntoBattlefield(1, game, source.getSourceId(), spell.getControllerId());
            }
        }
        return countered;
    }
}
