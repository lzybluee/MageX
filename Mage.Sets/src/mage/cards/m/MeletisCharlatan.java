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
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public class MeletisCharlatan extends CardImpl {

    public MeletisCharlatan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {2}{U}, {T}: The controller of target instant or sorcery spell copies it. That player may choose new targets for the copy.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MeletisCharlatanCopyTargetSpellEffect(), new ManaCostsImpl("{2}{U}"));
        ability.addCost(new TapSourceCost());
        Target target = new TargetSpell(StaticFilters.FILTER_INSTANT_OR_SORCERY_SPELL);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    public MeletisCharlatan(final MeletisCharlatan card) {
        super(card);
    }

    @Override
    public MeletisCharlatan copy() {
        return new MeletisCharlatan(this);
    }
}

class MeletisCharlatanCopyTargetSpellEffect extends OneShotEffect {

    public MeletisCharlatanCopyTargetSpellEffect() {
        super(Outcome.Copy);
        staticText = "The controller of target instant or sorcery spell copies it. That player may choose new targets for the copy";
    }

    public MeletisCharlatanCopyTargetSpellEffect(final MeletisCharlatanCopyTargetSpellEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        if (spell != null) {
            StackObject newStackObject = spell.createCopyOnStack(game, source, spell.getControllerId(), true);
            Player player = game.getPlayer(spell.getControllerId());
            if (player != null && newStackObject != null && newStackObject instanceof Spell) {
                String activateMessage = ((Spell) newStackObject).getActivatedMessage(game);
                if (activateMessage.startsWith(" casts ")) {
                    activateMessage = activateMessage.substring(6);
                }
                game.informPlayers(player.getLogName() + " copies " + activateMessage);
            }
            return true;
        }
        return false;
    }

    @Override
    public MeletisCharlatanCopyTargetSpellEffect copy() {
        return new MeletisCharlatanCopyTargetSpellEffect(this);
    }

}
