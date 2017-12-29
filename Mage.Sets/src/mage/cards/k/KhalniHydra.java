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
package mage.cards.k;

import java.util.Iterator;
import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public class KhalniHydra extends CardImpl {

    private static final FilterControlledCreaturePermanent filter;

    static {
        filter = new FilterControlledCreaturePermanent();
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public KhalniHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{G}{G}{G}{G}{G}{G}{G}");
        this.subtype.add(SubType.HYDRA);

        this.power = new MageInt(8);
        this.toughness = new MageInt(8);
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new KhalniHydraCostReductionEffect()));
        this.addAbility(TrampleAbility.getInstance());
    }

    public KhalniHydra(final KhalniHydra card) {
        super(card);
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        super.adjustCosts(ability, game);
        int reductionAmount = game.getBattlefield().count(filter,  ability.getSourceId(), ability.getControllerId(), game);
        Iterator<ManaCost> iter = ability.getManaCostsToPay().iterator();

        while ( reductionAmount > 0 && iter.hasNext() ) {
            ManaCost manaCost = iter.next();
            if (manaCost.getMana().getGreen() > 0) { // in case another effect adds additional mana cost
                iter.remove();
                reductionAmount--;
            }
        }
    }

    @Override
    public KhalniHydra copy() {
        return new KhalniHydra(this);
    }
}

class KhalniHydraCostReductionEffect extends OneShotEffect {
    private static final String effectText = "{this} costs {G} less to cast for each green creature you control";

    KhalniHydraCostReductionEffect ( ) {
        super(Outcome.Benefit);
        this.staticText = effectText;
    }

    KhalniHydraCostReductionEffect ( KhalniHydraCostReductionEffect effect ) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public KhalniHydraCostReductionEffect copy() {
        return new KhalniHydraCostReductionEffect(this);
    }

}
