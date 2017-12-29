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
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.common.TargetCardInOpponentsGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author North
 */
public class GruesomeEncore extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature card from an opponent's graveyard");

    public GruesomeEncore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");

        // Put target creature card from an opponent's graveyard onto the battlefield under your control. It gains haste.
        this.getSpellAbility().addEffect(new GruesomeEncoreEffect());
        // Exile it at the beginning of the next end step. If that creature would leave the battlefield, exile it instead of putting it anywhere else.
        this.getSpellAbility().addEffect(new GruesomeEncoreReplacementEffect());
        this.getSpellAbility().addTarget(new TargetCardInOpponentsGraveyard(filter));
    }

    public GruesomeEncore(final GruesomeEncore card) {
        super(card);
    }

    @Override
    public GruesomeEncore copy() {
        return new GruesomeEncore(this);
    }
}

class GruesomeEncoreEffect extends OneShotEffect {

    public GruesomeEncoreEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Put target creature card from an opponent's graveyard onto the battlefield under your control. It gains haste. Exile it at the beginning of the next end step";
    }

    public GruesomeEncoreEffect(final GruesomeEncoreEffect effect) {
        super(effect);
    }

    @Override
    public GruesomeEncoreEffect copy() {
        return new GruesomeEncoreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getFirstTarget());
        if (card != null) {
            card.putOntoBattlefield(game, Zone.GRAVEYARD, source.getSourceId(), source.getControllerId());

            ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
            effect.setTargetPointer(new FixedTarget(card.getId()));
            game.addEffect(effect, source);

            ExileTargetEffect exileEffect = new ExileTargetEffect();
            exileEffect.setTargetPointer(new FixedTarget(card.getId()));
            DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect);
            game.addDelayedTriggeredAbility(delayedAbility, source);

            return true;
        }

        return false;
    }
}

class GruesomeEncoreReplacementEffect extends ReplacementEffectImpl {

    GruesomeEncoreReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Tap);
        staticText = "If that creature would leave the battlefield, exile it instead of putting it anywhere else";
    }

    GruesomeEncoreReplacementEffect(final GruesomeEncoreReplacementEffect effect) {
        super(effect);
    }

    @Override
    public GruesomeEncoreReplacementEffect copy() {
        return new GruesomeEncoreReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Card card = game.getCard(source.getFirstTarget());
        if (card != null) {
            card.moveToExile(null, "", source.getSourceId(), game);
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getFirstTarget())
                && ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD
                && ((ZoneChangeEvent) event).getToZone() != Zone.EXILED;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }
}
