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
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class ArmoredTransport extends CardImpl {

    public ArmoredTransport(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Prevent all combat damage that would be dealt to Armored Transport by creatures blocking it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ArmoredTransportPreventCombatDamageSourceEffect(Duration.WhileOnBattlefield)));

    }

    public ArmoredTransport(final ArmoredTransport card) {
        super(card);
    }

    @Override
    public ArmoredTransport copy() {
        return new ArmoredTransport(this);
    }
}

class ArmoredTransportPreventCombatDamageSourceEffect extends PreventionEffectImpl {

    public ArmoredTransportPreventCombatDamageSourceEffect(Duration duration) {
        super(duration);
        staticText = "Prevent all combat damage that would be dealt to {this} by creatures blocking it" + duration.toString();
    }

    public ArmoredTransportPreventCombatDamageSourceEffect(final ArmoredTransportPreventCombatDamageSourceEffect effect) {
        super(effect);
    }

    @Override
    public ArmoredTransportPreventCombatDamageSourceEffect copy() {
        return new ArmoredTransportPreventCombatDamageSourceEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            DamageEvent damageEvent = (DamageEvent) event;
            if (event.getTargetId().equals(source.getSourceId()) && damageEvent.isCombatDamage()) {
                Permanent sourcePermanent = game.getPermanent(source.getSourceId());
                if (sourcePermanent != null && sourcePermanent.isAttacking()) {
                    return true;
                }
            }
        }
        return false;
    }
}
