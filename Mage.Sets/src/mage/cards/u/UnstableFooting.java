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
package mage.cards.u;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author LevelX2
 */
public class UnstableFooting extends CardImpl {

    public UnstableFooting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Kicker {3}{R} (You may pay an additional {3}{R} as you cast this spell.)
        this.addAbility(new KickerAbility("{3}{R}"));

        // Damage can't be prevented this turn. If Unstable Footing was kicked, it deals 5 damage to target player.
        this.getSpellAbility().addEffect(new UnstableFootingEffect());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(5),
                KickedCondition.instance,
                "If this spell was kicked, it deals 5 damage to target player or planeswalker"));

    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            if (KickedCondition.instance.apply(game, ability)) {
                ability.addTarget(new TargetPlayerOrPlaneswalker());
            }
        }
    }

    public UnstableFooting(final UnstableFooting card) {
        super(card);
    }

    @Override
    public UnstableFooting copy() {
        return new UnstableFooting(this);
    }
}

class UnstableFootingEffect extends ReplacementEffectImpl {

    public UnstableFootingEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "Damage can't be prevented this turn";
    }

    public UnstableFootingEffect(final UnstableFootingEffect effect) {
        super(effect);
    }

    @Override
    public UnstableFootingEffect copy() {
        return new UnstableFootingEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.PREVENT_DAMAGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }

}
