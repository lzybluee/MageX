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

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class DragonsoulKnight extends CardImpl {

    public DragonsoulKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // {W}{U}{B}{R}{G}: Until end of turn, Dragonsoul Knight becomes a Dragon, gets +5/+3, and gains flying and trample.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DragonsoulKnightEffect(), new ManaCostsImpl("{W}{U}{B}{R}{G}"));
        Effect effect = new BoostSourceEffect(5, 3, Duration.EndOfTurn);
        effect.setText("gets +5/+3");
        ability.addEffect(effect);
        effect = new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains flying");
        ability.addEffect(effect);
        effect = new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and trample");
        ability.addEffect(effect);
        this.addAbility(ability);

    }

    public DragonsoulKnight(final DragonsoulKnight card) {
        super(card);
    }

    @Override
    public DragonsoulKnight copy() {
        return new DragonsoulKnight(this);
    }

    private class DragonsoulKnightEffect extends ContinuousEffectImpl {

        public DragonsoulKnightEffect() {
            super(Duration.EndOfTurn, Outcome.BecomeCreature);
            setText();
        }

        public DragonsoulKnightEffect(final DragonsoulKnightEffect effect) {
            super(effect);
        }

        @Override
        public DragonsoulKnightEffect copy() {
            return new DragonsoulKnightEffect(this);
        }

        @Override
        public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent == null) {
                return false;
            }
            switch (layer) {
                case TypeChangingEffects_4:
                    if (sublayer == SubLayer.NA) {
                        permanent.getSubtype(game).clear();
                        permanent.getSubtype(game).add(SubType.DRAGON);
                    }
                    break;
            }
            return true;
        }

        @Override
        public boolean apply(Game game, Ability source) {
            return false;
        }

        private void setText() {
            staticText = "Until end of turn, {this} becomes a Dragon, ";
        }

        @Override
        public boolean hasLayer(Layer layer) {
            return layer == Layer.TypeChangingEffects_4;
        }
    }
}
