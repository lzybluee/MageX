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
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.PreventDamageToSourceEffect;
import mage.abilities.keyword.BushidoAbility;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetSource;

/**
 * @author LevelX2
 */
public final class OpalEyeKondasYojimbo extends CardImpl {

    public OpalEyeKondasYojimbo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.SAMURAI);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Defender (This creature can't attack.)
        this.addAbility(DefenderAbility.getInstance());

        // Bushido 1 (When this blocks or becomes blocked, it gets +1/+1 until end of turn.)
        this.addAbility(new BushidoAbility(1));

        // {T}: The next time a source of your choice would deal damage this turn, that damage is dealt to Opal-Eye, Konda's Yojimbo instead.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new OpalEyeKondasYojimboRedirectionEffect(), new TapSourceCost()));

        // {1}{W}: Prevent the next 1 damage that would be dealt to Opal-Eye this turn.        
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageToSourceEffect(Duration.EndOfTurn, 1), new ManaCostsImpl("{1}{W}")));

    }

    public OpalEyeKondasYojimbo(final OpalEyeKondasYojimbo card) {
        super(card);
    }

    @Override
    public OpalEyeKondasYojimbo copy() {
        return new OpalEyeKondasYojimbo(this);
    }
}

class OpalEyeKondasYojimboRedirectionEffect extends ReplacementEffectImpl {
    
    private final TargetSource target;
    
    OpalEyeKondasYojimboRedirectionEffect() {
        super(Duration.EndOfTurn, Outcome.RedirectDamage);
        staticText = "The next time a source of your choice would deal damage this turn, that damage is dealt to {this} instead";
        this.target = new TargetSource();
    }

    OpalEyeKondasYojimboRedirectionEffect(final OpalEyeKondasYojimboRedirectionEffect effect) {
        super(effect);
        this.target = effect.target.copy();
    }
    
    @Override
    public void init(Ability source, Game game) {
        this.target.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), game);
        super.init(source, game);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_CREATURE ||
                event.getType() == GameEvent.EventType.DAMAGE_PLANESWALKER ||
                event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getSourceId().equals(target.getFirstTarget())) {
            // check source
            MageObject object = game.getObject(event.getSourceId());
            if (object == null) {
                game.informPlayers("Couldn't find source of damage");
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamageEvent damageEvent = (DamageEvent)event;
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            // get name of old target
            Permanent targetPermanent = game.getPermanent(event.getTargetId());
            StringBuilder message = new StringBuilder();
            message.append(sourcePermanent.getName()).append(": gets ");
            message.append(damageEvent.getAmount()).append(" damage redirected from ");
            if (targetPermanent != null) {
                message.append(targetPermanent.getName());
            }
            else {
                Player targetPlayer = game.getPlayer(event.getTargetId());
                if (targetPlayer != null) {
                    message.append(targetPlayer.getLogName());
                }
                else {
                    message.append("unknown");
                }
            }
            game.informPlayers(message.toString());
            // redirect damage
            discard();
            sourcePermanent.damage(damageEvent.getAmount(), damageEvent.getSourceId(), game, damageEvent.isCombatDamage(), damageEvent.isPreventable(), event.getAppliedEffects());
            return true;
        }
        return false;
    }

    @Override
    public OpalEyeKondasYojimboRedirectionEffect copy() {
        return new OpalEyeKondasYojimboRedirectionEffect(this);
    }
}
