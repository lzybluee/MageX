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
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.WurmCallingWurmToken;

/**
 *
 * @author LoneFox
 */
public class Wurmcalling extends CardImpl {

    public Wurmcalling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}");

        // Buyback {2}{G}
        this.addAbility(new BuybackAbility("{2}{G}"));
        // Create an X/X green Wurm creature token.
        this.getSpellAbility().addEffect(new WurmcallingEffect());
    }

    public Wurmcalling(final Wurmcalling card) {
        super(card);
    }

    @Override
    public Wurmcalling copy() {
        return new Wurmcalling(this);
    }
}

class WurmcallingEffect extends OneShotEffect {

    public WurmcallingEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Create an X/X green Wurm creature token";
    }

    public WurmcallingEffect(WurmcallingEffect ability) {
        super(ability);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int count = source.getManaCostsToPay().getX();
        WurmCallingWurmToken token = new WurmCallingWurmToken();
        token.getPower().modifyBaseValue(count);
        token.getToughness().modifyBaseValue(count);
        token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
        return true;
    }

    @Override
    public WurmcallingEffect copy() {
        return new WurmcallingEffect(this);
    }
}
