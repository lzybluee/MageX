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

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.BushidoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX
 */
public class SamuraiOfThePaleCurtain extends CardImpl {

    public SamuraiOfThePaleCurtain (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.SAMURAI);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        
        // Bushido 1 (When this blocks or becomes blocked, it gets +1/+1 until end of turn.)
        this.addAbility(new BushidoAbility(1));
        // If a permanent would be put into a graveyard, exile it instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SamuraiOfThePaleCurtainEffect()));

    }

    public SamuraiOfThePaleCurtain (final SamuraiOfThePaleCurtain card) {
        super(card);
    }

    @Override
    public SamuraiOfThePaleCurtain copy() {
        return new SamuraiOfThePaleCurtain(this);
    }

}


class SamuraiOfThePaleCurtainEffect extends ReplacementEffectImpl {

    public SamuraiOfThePaleCurtainEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "If a permanent would be put into a graveyard, exile it instead";
    }

    public SamuraiOfThePaleCurtainEffect(final SamuraiOfThePaleCurtainEffect effect) {
        super(effect);
    }

    @Override
    public SamuraiOfThePaleCurtainEffect copy() {
        return new SamuraiOfThePaleCurtainEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((ZoneChangeEvent)event).getTarget();
        if (permanent != null) {
            return permanent.moveToExile(null, "", source.getSourceId(), game);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
        return zEvent.getToZone() == Zone.GRAVEYARD;
    }

}
