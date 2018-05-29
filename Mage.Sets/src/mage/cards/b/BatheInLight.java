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
package mage.cards.b;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.FilterObject;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2 & L_J
 */
public final class BatheInLight extends CardImpl {

    public BatheInLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Radiance - Choose a color. Target creature and each other creature that shares a color with it gain protection from the chosen color until end of turn.
        this.getSpellAbility().addEffect(new BatheInLightEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().setAbilityWord(AbilityWord.RADIANCE);
    }

    public BatheInLight(final BatheInLight card) {
        super(card);
    }

    @Override
    public BatheInLight copy() {
        return new BatheInLight(this);
    }
}

class BatheInLightEffect extends OneShotEffect {

    public BatheInLightEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target creature and each other creature that shares a color with it gain protection from the chosen color until end of turn";
    }

    public BatheInLightEffect(final BatheInLightEffect effect) {
        super(effect);
    }

    @Override
    public BatheInLightEffect copy() {
        return new BatheInLightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent target = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (target != null) {
                ChoiceColor colorChoice = new ChoiceColor();
                if (!controller.choose(Outcome.Benefit, colorChoice, game)) {
                    return false;
                }
                game.informPlayers(target.getName() + ": " + controller.getLogName() + " has chosen " + colorChoice.getChoice());
                game.getState().setValue(target.getId() + "_color", colorChoice.getColor());

                ObjectColor protectColor = (ObjectColor) game.getState().getValue(target.getId() + "_color");
                if (protectColor != null) {
                    ContinuousEffect effect = new ProtectionChosenColorTargetEffect();
                    game.addEffect(effect, source);
                    ObjectColor color = target.getColor(game);
                    for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source.getSourceId(), game)) {
                        if (!permanent.getId().equals(target.getId()) && permanent.getColor(game).shares(color)) {
                            game.getState().setValue(permanent.getId() + "_color", colorChoice.getColor());
                            effect.setTargetPointer(new FixedTarget(permanent, game));
                            game.addEffect(effect, source);
                        }
                    }

                }
                return true;
            }
        }
        return false;
    }
}

class ProtectionChosenColorTargetEffect extends ContinuousEffectImpl {

    protected ObjectColor chosenColor;
    protected ProtectionAbility protectionAbility;

    public ProtectionChosenColorTargetEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
    }

    public ProtectionChosenColorTargetEffect(final ProtectionChosenColorTargetEffect effect) {
        super(effect);
        if (effect.chosenColor != null) {
            this.chosenColor = effect.chosenColor.copy();
        }
        if (effect.protectionAbility != null) {
            this.protectionAbility = effect.protectionAbility.copy();
        }
    }

    @Override
    public ProtectionChosenColorTargetEffect copy() {
        return new ProtectionChosenColorTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            ObjectColor color = (ObjectColor) game.getState().getValue(permanent.getId() + "_color");
            if (color != null && (protectionAbility == null || !color.equals(chosenColor))) {
                chosenColor = color;
                FilterObject protectionFilter = new FilterObject(chosenColor.getDescription());
                protectionFilter.add(new ColorPredicate(chosenColor));
                protectionAbility = new ProtectionAbility(protectionFilter);
            }
            if (protectionAbility != null) {
                permanent.addAbility(protectionAbility, source.getSourceId(), game);
                return true;
            }
        }
        return false;
    }
}
