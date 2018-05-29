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
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessTargetEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.SubLayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class WingsOfVelisVel extends CardImpl {

    public WingsOfVelisVel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.INSTANT},"{1}{U}");
        this.subtype.add(SubType.SHAPESHIFTER);

        // Changeling
        this.addAbility(ChangelingAbility.getInstance());

        // Target creature becomes 4/4, gains all creature types, and gains flying until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        Effect effect = new SetPowerToughnessTargetEffect(4, 4, Duration.EndOfTurn);
        effect.setText("Target creature becomes 4/4");
        this.getSpellAbility().addEffect(effect);

        effect = new GainAbilityTargetEffect(ChangelingAbility.getInstance(), Duration.EndOfTurn, null, false, Layer.TypeChangingEffects_4, SubLayer.NA);
        effect.setText(", gains all creature types");
        this.getSpellAbility().addEffect(effect);

        effect = new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);
        effect.setText(", and gains flying until end of turn");
        this.getSpellAbility().addEffect(effect);
    }

    public WingsOfVelisVel(final WingsOfVelisVel card) {
        super(card);
    }

    @Override
    public WingsOfVelisVel copy() {
        return new WingsOfVelisVel(this);
    }
}
