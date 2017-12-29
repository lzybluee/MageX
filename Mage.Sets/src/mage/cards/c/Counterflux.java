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
package mage.cards.c;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CantBeCounteredSourceEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.target.TargetSpell;


/**
 *
 * @author LevelX2
 */
public class Counterflux extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell you don't control");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public Counterflux(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}{U}{R}");


        // Counterflux can't be countered by spells or abilities.
        Effect effect =  new CantBeCounteredSourceEffect();
        effect.setText("{this} can't be countered by spells or abilities");
        Ability ability = new SimpleStaticAbility(Zone.STACK,effect);
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // Counter target spell you don't control.
        this.getSpellAbility().addTarget(new TargetSpell(filter));
        this.getSpellAbility().addEffect(new CounterTargetEffect());

        // Overload {1}{U}{U}{R} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of "target" with "each.")
        this.addAbility(new OverloadAbility(this, new CounterfluxEffect(), new ManaCostsImpl("{1}{U}{U}{R}")));
    }

    public Counterflux(final Counterflux card) {
        super(card);
    }

    @Override
    public Counterflux copy() {
        return new Counterflux(this);
    }
}

class CounterfluxEffect extends OneShotEffect {

    public CounterfluxEffect() {
        super(Outcome.Detriment);
        staticText = "Counter each spell you don't control.";

    }

    public CounterfluxEffect(final CounterfluxEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        List<Spell> spellsToCounter = new LinkedList<>();
        for (StackObject stackObject : game.getStack()) {
            if (stackObject instanceof Spell && !stackObject.getControllerId().equals(source.getControllerId())) {
                spellsToCounter.add((Spell) stackObject);
            }
        }
        for (Spell spell : spellsToCounter) {
            game.getStack().counter(spell.getId(), source.getSourceId(), game);
        }
        return true;
    }

    @Override
    public CounterfluxEffect copy() {
        return new CounterfluxEffect(this);
    }

}