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
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public final class OddlyUneven extends CardImpl {

    public OddlyUneven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        // Choose one --
        // * Destroy each creature with an odd number of words in its name. (Hyphenated words are one word.)
        this.getSpellAbility().addEffect(new OddOrEvenEffect(true));
        // * Destroy each creature with an even number of words in its name.
        Mode mode = new Mode();
        mode.getEffects().add(new OddOrEvenEffect(false));
        this.getSpellAbility().addMode(mode);
    }

    public OddlyUneven(final OddlyUneven card) {
        super(card);
    }

    @Override
    public OddlyUneven copy() {
        return new OddlyUneven(this);
    }
}

class OddOrEvenEffect extends OneShotEffect {

    private boolean odd = true;

    public OddOrEvenEffect(boolean odd) {
        super(Outcome.DestroyPermanent);
        this.odd = odd;
        this.staticText = "Destroy each creature with an " + (odd ? "odd" : "even") + " number of words in its name. (Hyphenated words are one word.)";
    }

    public OddOrEvenEffect(final OddOrEvenEffect effect) {
        super(effect);
        this.odd = effect.odd;
    }

    @Override
    public OddOrEvenEffect copy() {
        return new OddOrEvenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (Permanent creature : game.getState().getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, controller.getId(), game)) {
                // Check the number of words in the name (based on number of spaces)
                if (creature != null) {
                    String name = creature.getName();

                    if (name.equalsIgnoreCase("") && this.odd == false) {
                        creature.destroy(source.getSourceId(), game, false);
                    } else {
                        int spaces = name.length() - name.replace(" ", "").length();
                        boolean nameIsOdd = (spaces % 2 == 0);
                        if (this.odd && nameIsOdd || !this.odd && !nameIsOdd) {
                            creature.destroy(source.getSourceId(), game, false);
                        }
                    }
                }
            }
        }

        return false;
    }
}
