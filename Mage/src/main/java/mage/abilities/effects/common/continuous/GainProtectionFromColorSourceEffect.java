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
package mage.abilities.effects.common.continuous;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.keyword.ProtectionAbility;
import mage.choices.ChoiceColor;
import mage.constants.Duration;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class GainProtectionFromColorSourceEffect extends GainAbilitySourceEffect {

    FilterCard protectionFilter;

    public GainProtectionFromColorSourceEffect(Duration duration) {
        super(new ProtectionAbility(new FilterCard()), duration);
        protectionFilter = (FilterCard) ((ProtectionAbility) ability).getFilter();
    }

    public GainProtectionFromColorSourceEffect(final GainProtectionFromColorSourceEffect effect) {
        super(effect);
        this.protectionFilter = effect.protectionFilter.copy();
    }

    @Override
    public GainProtectionFromColorSourceEffect copy() {
        return new GainProtectionFromColorSourceEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ChoiceColor colorChoice = new ChoiceColor(true);
            colorChoice.setMessage("Choose color for protection ability");
            if (controller.choose(outcome, colorChoice, game)) {
                game.informPlayers("Choosen color: " + colorChoice.getColor());
                protectionFilter.add(new ColorPredicate(colorChoice.getColor()));
                protectionFilter.setMessage(colorChoice.getChoice());
                ((ProtectionAbility) ability).setFilter(protectionFilter);
                return;
            }
        }
        discard();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && new MageObjectReference(permanent, game).refersTo(source.getSourceObject(game), game)) {
            permanent.addAbility(ability, source.getSourceId(), game);
        } else {
            // the source permanent is no longer on the battlefield, effect can be discarded
            discard();
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return "{this} gains protection from the color of your choice " + duration.toString();
    }
}
