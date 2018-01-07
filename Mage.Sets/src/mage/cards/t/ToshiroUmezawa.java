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
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.BushidoAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public class ToshiroUmezawa extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature an opponent controls");
    private static final FilterCard filterInstant = new FilterCard("instant card from your graveyard");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
        filterInstant.add(new CardTypePredicate(CardType.INSTANT));
    }

    public ToshiroUmezawa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Bushido 1
        this.addAbility(new BushidoAbility(1));
        // Whenever a creature an opponent controls dies, you may cast target instant card from your graveyard. If that card would be put into a graveyard this turn, exile it instead.
        Ability ability = new DiesCreatureTriggeredAbility(new ToshiroUmezawaEffect(), true, filter);
        ability.addTarget(new TargetCardInYourGraveyard(1, 1, filterInstant));
        this.addAbility(ability);

    }

    public ToshiroUmezawa(final ToshiroUmezawa card) {
        super(card);
    }

    @Override
    public ToshiroUmezawa copy() {
        return new ToshiroUmezawa(this);
    }
}

class ToshiroUmezawaEffect extends OneShotEffect {

    public ToshiroUmezawaEffect() {
        super(Outcome.Benefit);
        this.staticText = "cast target instant card from your graveyard. If that card would be put into a graveyard this turn, exile it instead";
    }

    public ToshiroUmezawaEffect(final ToshiroUmezawaEffect effect) {
        super(effect);
    }

    @Override
    public ToshiroUmezawaEffect copy() {
        return new ToshiroUmezawaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(getTargetPointer().getFirst(game, source));
            if (card != null) {
                controller.cast(card.getSpellAbility(), game, false);
                game.addEffect(new ToshiroUmezawaReplacementEffect(card.getId()), source);
            }
        }
        return false;
    }
}

class ToshiroUmezawaReplacementEffect extends ReplacementEffectImpl {

    private final UUID cardId;

    public ToshiroUmezawaReplacementEffect(UUID cardId) {
        super(Duration.EndOfTurn, Outcome.Exile);
        this.cardId = cardId;
    }

    public ToshiroUmezawaReplacementEffect(final ToshiroUmezawaReplacementEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
    }

    @Override
    public ToshiroUmezawaReplacementEffect copy() {
        return new ToshiroUmezawaReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        UUID eventObject = ((ZoneChangeEvent) event).getTargetId();
        StackObject stackObject = game.getStack().getStackObject(eventObject);
        if (stackObject != null) {
            if (stackObject instanceof Spell) {
                game.rememberLKI(stackObject.getId(), Zone.STACK, (Spell) stackObject);
            }
            if (stackObject instanceof Card && eventObject.equals(cardId)) {
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
                && ((ZoneChangeEvent) event).getTargetId().equals(cardId);
    }
}
