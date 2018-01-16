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
package mage.cards.d;

import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.common.TargetCardInOpponentsGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class DireFleetDaredevil extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant or sorcery card from an opponent's graveyard");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    public DireFleetDaredevil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // When this enters the battlefield, exile target instant or sorcery card from an opponent's graveyard. You may cast that card this turn and you may spend mana as though it were mana of any color. If that card would be put into a graveyard this turn, exile it instead.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DireFleetDaredevilEffect());
        ability.addTarget(new TargetCardInOpponentsGraveyard(filter));
        this.addAbility(ability);
    }

    public DireFleetDaredevil(final DireFleetDaredevil card) {
        super(card);
    }

    @Override
    public DireFleetDaredevil copy() {
        return new DireFleetDaredevil(this);
    }
}

class DireFleetDaredevilEffect extends OneShotEffect {

    public DireFleetDaredevilEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile target instant or sorcery card from an opponent's graveyard. You may cast that card this turn and you may spend mana as though it were mana of any color. If that card would be put into a graveyard this turn, exile it instead";
    }

    public DireFleetDaredevilEffect(final DireFleetDaredevilEffect effect) {
        super(effect);
    }

    @Override
    public DireFleetDaredevilEffect copy() {
        return new DireFleetDaredevilEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card targetCard = game.getCard(getTargetPointer().getFirst(game, source));
            if (targetCard != null) {
                if (controller.moveCards(targetCard, Zone.EXILED, source, game)) {
                    targetCard = game.getCard(targetCard.getId());
                    if (targetCard != null) {
                        ContinuousEffect effect = new DireFleetDaredevilPlayEffect();
                        effect.setTargetPointer(new FixedTarget(targetCard.getId(), targetCard.getZoneChangeCounter(game)));
                        game.addEffect(effect, source);
                        effect = new DireFleetDaredevilSpendAnyManaEffect();
                        effect.setTargetPointer(new FixedTarget(targetCard.getId(), targetCard.getZoneChangeCounter(game)));
                        game.addEffect(effect, source);
                        effect = new DireFleetDaredevilReplacementEffect();
                        effect.setTargetPointer(new FixedTarget(targetCard.getId(), targetCard.getZoneChangeCounter(game)));
                        game.addEffect(effect, source);

                    }
                }
            }
            return true;
        }
        return false;
    }
}

class DireFleetDaredevilPlayEffect extends AsThoughEffectImpl {

    public DireFleetDaredevilPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "You may cast that card this turn";
    }

    public DireFleetDaredevilPlayEffect(final DireFleetDaredevilPlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public DireFleetDaredevilPlayEffect copy() {
        return new DireFleetDaredevilPlayEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        UUID targetId = getTargetPointer().getFirst(game, source);
        if (targetId != null) {
            return targetId.equals(objectId)
                    && source.getControllerId().equals(affectedControllerId);
        } else {
            // the target card has changed zone meanwhile, so the effect is no longer needed
            discard();
            return false;
        }
    }
}

class DireFleetDaredevilSpendAnyManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    public DireFleetDaredevilSpendAnyManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "you may spend mana as though it were mana of any color";
    }

    public DireFleetDaredevilSpendAnyManaEffect(final DireFleetDaredevilSpendAnyManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public DireFleetDaredevilSpendAnyManaEffect copy() {
        return new DireFleetDaredevilSpendAnyManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return source.getControllerId().equals(affectedControllerId)
                && Objects.equals(objectId, ((FixedTarget) getTargetPointer()).getTarget())
                && ((FixedTarget) getTargetPointer()).getZoneChangeCounter() + 1 == game.getState().getZoneChangeCounter(objectId)
                && game.getState().getZone(objectId) == Zone.STACK;
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }

}

class DireFleetDaredevilReplacementEffect extends ReplacementEffectImpl {

    public DireFleetDaredevilReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Exile);
        staticText = "If that card would be put into a graveyard this turn, exile it instead";
    }

    public DireFleetDaredevilReplacementEffect(final DireFleetDaredevilReplacementEffect effect) {
        super(effect);
    }

    @Override
    public DireFleetDaredevilReplacementEffect copy() {
        return new DireFleetDaredevilReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        UUID eventObject = ((ZoneChangeEvent) event).getTargetId();
        StackObject stackObject = game.getStack().getStackObject(eventObject);
        if (stackObject != null) {
            if (stackObject instanceof Spell) {
                game.rememberLKI(stackObject.getId(), Zone.STACK, (Spell) stackObject);
            }
            if (stackObject instanceof Card
                    && stackObject.getSourceId().equals(((FixedTarget) getTargetPointer()).getTarget())
                    && ((FixedTarget) getTargetPointer()).getZoneChangeCounter() + 1 == game.getState().getZoneChangeCounter(stackObject.getSourceId())
                    && game.getState().getZone(stackObject.getSourceId()) == Zone.STACK) {
                ((Card) stackObject).moveToExile(null, null, source.getSourceId(), game);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.getToZone() == Zone.GRAVEYARD
                && ((ZoneChangeEvent) event).getTargetId().equals(((FixedTarget) getTargetPointer()).getTarget());
    }
}
