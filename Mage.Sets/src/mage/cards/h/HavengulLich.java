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
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author BetaSteward
 */
public final class HavengulLich extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card in a graveyard");

    public HavengulLich(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {1}: You may cast target creature card in a graveyard this turn. When you cast that card this turn, Havengul Lich gains all activated abilities of that card until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HavengulLichPlayEffect(), new ManaCostsImpl("{1}"));
        ability.addEffect(new HavengulLichPlayedEffect());
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.addAbility(ability);

    }

    public HavengulLich(final HavengulLich card) {
        super(card);
    }

    @Override
    public HavengulLich copy() {
        return new HavengulLich(this);
    }
}

//allow card in graveyard to be played
class HavengulLichPlayEffect extends AsThoughEffectImpl {

    public HavengulLichPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "You may cast target creature card in a graveyard this turn";
    }

    public HavengulLichPlayEffect(final HavengulLichPlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public HavengulLichPlayEffect copy() {
        return new HavengulLichPlayEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        UUID targetId = getTargetPointer().getFirst(game, source);
        if (targetId != null) {
            return targetId.equals(objectId)
                    && source.getControllerId().equals(affectedControllerId)
                    && Zone.GRAVEYARD == game.getState().getZone(objectId);
        } else {
            // the target card has changed zone meanwhile, so the effect is no longer needed
            discard();
            return false;
        }
    }
}

//create delayed triggered ability to watch for card being played
class HavengulLichPlayedEffect extends OneShotEffect {

    public HavengulLichPlayedEffect() {
        super(Outcome.PutCreatureInPlay);
    }

    public HavengulLichPlayedEffect(final HavengulLichPlayedEffect effect) {
        super(effect);
        staticText = "When you cast that card this turn, {this} gains all activated abilities of that card until end of turn";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DelayedTriggeredAbility ability = new HavengulLichDelayedTriggeredAbility(getTargetPointer().getFirst(game, source));
        game.addDelayedTriggeredAbility(ability, source);
        return true;
    }

    @Override
    public HavengulLichPlayedEffect copy() {
        return new HavengulLichPlayedEffect(this);
    }

}

// when card is played create continuous effect
class HavengulLichDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private UUID cardId;

    public HavengulLichDelayedTriggeredAbility(UUID cardId) {
        super(new HavengulLichEffect(cardId), Duration.EndOfTurn);
        this.cardId = cardId;
    }

    public HavengulLichDelayedTriggeredAbility(HavengulLichDelayedTriggeredAbility ability) {
        super(ability);
        this.cardId = ability.cardId;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId().equals(cardId);
    }

    @Override
    public HavengulLichDelayedTriggeredAbility copy() {
        return new HavengulLichDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When you cast that card this turn, {this} gains all activated abilities of that card until end of turn.";
    }
}

// copy activated abilities of card
class HavengulLichEffect extends ContinuousEffectImpl {

    private final UUID cardId;

    public HavengulLichEffect(UUID cardId) {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.cardId = cardId;
    }

    public HavengulLichEffect(final HavengulLichEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
    }

    @Override
    public HavengulLichEffect copy() {
        return new HavengulLichEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Card card = game.getCard(cardId);
        if (permanent != null && card != null) {
            for (ActivatedAbility ability : card.getAbilities().getActivatedAbilities(Zone.BATTLEFIELD)) {
                permanent.addAbility(ability, source.getSourceId(), game);
            }
        }
        return false;
    }
}
