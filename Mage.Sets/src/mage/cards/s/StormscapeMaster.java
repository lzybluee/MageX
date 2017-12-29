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
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.GainProtectionFromColorTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 *
 */
public class StormscapeMaster extends CardImpl {

    public StormscapeMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {W}{W}, {T}: Target creature gains protection from the color of your choice until end of turn.
        Effect effect = new GainProtectionFromColorTargetEffect(Duration.EndOfTurn);
        effect.setText("Target creature gains protection from the color of your choice until end of turn.");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{W}{W}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {B}{B}, {T}: Target player loses 2 life and you gain 2 life.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseLifeTargetEffect(2), new ManaCostsImpl("{B}{B}"));
        ability.addEffect(new GainLifeEffect(2).setText("and you gain 2 life"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public StormscapeMaster(final StormscapeMaster card) {
        super(card);
    }

    @Override
    public StormscapeMaster copy() {
        return new StormscapeMaster(this);
    }
}
