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
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.token.TokenImpl;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.CastSpellLastTurnWatcher;

/**
 *
 * @author LevelX2
 */
public class ErayoSoratamiAscendant extends CardImpl {

    public ErayoSoratamiAscendant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.MOONFOLK);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.flipCard = true;
        this.flipCardName = "Erayo's Essence";

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever the fourth spell of a turn is cast, flip Erayo, Soratami Ascendant.
        this.addAbility(new ErayoSoratamiAscendantTriggeredAbility());

    }

    public ErayoSoratamiAscendant(final ErayoSoratamiAscendant card) {
        super(card);
    }

    @Override
    public ErayoSoratamiAscendant copy() {
        return new ErayoSoratamiAscendant(this);
    }
}

class ErayoSoratamiAscendantTriggeredAbility extends TriggeredAbilityImpl {

    public ErayoSoratamiAscendantTriggeredAbility() {
        super(Zone.BATTLEFIELD, getFlipEffect(), false);
    }

    private static Effect getFlipEffect() {
        Effect effect = new FlipSourceEffect(new ErayosEssenceToken());
        effect.setText("flip {this}");
        return effect;
    }

    public ErayoSoratamiAscendantTriggeredAbility(final ErayoSoratamiAscendantTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        CastSpellLastTurnWatcher watcher = (CastSpellLastTurnWatcher) game.getState().getWatchers().get(CastSpellLastTurnWatcher.class.getSimpleName());
        return watcher != null && watcher.getAmountOfSpellsAllPlayersCastOnCurrentTurn() == 4;
    }

    @Override
    public String getRule() {
        return "Whenever the fourth spell of a turn is cast, " + super.getRule();
    }

    @Override
    public ErayoSoratamiAscendantTriggeredAbility copy() {
        return new ErayoSoratamiAscendantTriggeredAbility(this);
    }
}

class ErayosEssenceToken extends TokenImpl {

    ErayosEssenceToken() {
        super("Erayo's Essence", "");
        addSuperType(SuperType.LEGENDARY);
        cardType.add(CardType.ENCHANTMENT);

        color.setBlue(true);

        // Whenever an opponent casts a spell for the first time in a turn, counter that spell.
        Effect effect = new CounterTargetEffect();
        effect.setText("counter that spell");
        this.addAbility(new ErayosEssenceTriggeredAbility(effect));
    }
    public ErayosEssenceToken(final ErayosEssenceToken token) {
        super(token);
    }

    public ErayosEssenceToken copy() {
        return new ErayosEssenceToken(this);
    }
}

class ErayosEssenceTriggeredAbility extends TriggeredAbilityImpl {

    public ErayosEssenceTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
    }

    public ErayosEssenceTriggeredAbility(final ErayosEssenceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(getControllerId()).contains(event.getPlayerId())) {
            CastSpellLastTurnWatcher watcher = (CastSpellLastTurnWatcher) game.getState().getWatchers().get(CastSpellLastTurnWatcher.class.getSimpleName());
            if (watcher != null && watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(event.getPlayerId()) == 1) {
                for (Effect effect : getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts a spell for the first time each turn, " + super.getRule();
    }

    @Override
    public ErayosEssenceTriggeredAbility copy() {
        return new ErayosEssenceTriggeredAbility(this);
    }
}
