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

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.watchers.common.CreatureWasCastWatcher;

/**
 *
 * @author LevelX2
 */
public class ContainmentPriest extends CardImpl {

    public ContainmentPriest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // If a nontoken creature would enter the battlefield and it wasn't cast, exile it instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ContainmentPriestReplacementEffect()), new CreatureWasCastWatcher());
    }

    public ContainmentPriest(final ContainmentPriest card) {
        super(card);
    }

    @Override
    public ContainmentPriest copy() {
        return new ContainmentPriest(this);
    }
}

class ContainmentPriestReplacementEffect extends ReplacementEffectImpl {

    public ContainmentPriestReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "If a nontoken creature would enter the battlefield and it wasn't cast, exile it instead";
    }

    public ContainmentPriestReplacementEffect(final ContainmentPriestReplacementEffect effect) {
        super(effect);
    }

    @Override
    public ContainmentPriestReplacementEffect copy() {
        return new ContainmentPriestReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (controller != null) {
            Card card = game.getCard(event.getTargetId());
            if (card != null) {
                controller.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, zEvent.getFromZone(), true);
            }
            return true;

        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE; // Token create the create Token event
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (((ZoneChangeEvent) event).getToZone() == Zone.BATTLEFIELD) {
            Card card = game.getCard(event.getTargetId());
            Object entersTransformed = game.getState().getValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + event.getTargetId());
            if (entersTransformed instanceof Boolean && (Boolean) entersTransformed && card.getSecondCardFace() != null) {
                card = card.getSecondCardFace();
            }
            if (card.isCreature()) { // TODO: Bestow Card cast as Enchantment probably not handled correctly
                CreatureWasCastWatcher watcher = (CreatureWasCastWatcher) game.getState().getWatchers().get(CreatureWasCastWatcher.class.getSimpleName());
                if (watcher != null && !watcher.wasCreatureCastThisTurn(event.getTargetId())) {
                    return true;
                }
            }
        }
        return false;
    }
}
