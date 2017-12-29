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
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.PlayLandAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class TsabosWeb extends CardImpl {

    public TsabosWeb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // When Tsabo's Web enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1), false));
        // Each land with an activated ability that isn't a mana ability doesn't untap during its controller's untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TsabosWebPreventUntapEffect()));
    }

    public TsabosWeb(final TsabosWeb card) {
        super(card);
    }

    @Override
    public TsabosWeb copy() {
        return new TsabosWeb(this);
    }
}

class TsabosWebPreventUntapEffect extends ContinuousRuleModifyingEffectImpl {

    public TsabosWebPreventUntapEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Each land with an activated ability that isn't a mana ability doesn't untap during its controller's untap step";
    }

    public TsabosWebPreventUntapEffect(final TsabosWebPreventUntapEffect effect) {
        super(effect);
    }

    @Override
    public TsabosWebPreventUntapEffect copy() {
        return new TsabosWebPreventUntapEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAP;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getTurn().getStepType() == PhaseStep.UNTAP) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.isLand()) {
                for (Ability ability :permanent.getAbilities()) {
                    if (!(ability instanceof PlayLandAbility)
                            && !(ability instanceof ActivatedManaAbilityImpl)
                            && ability instanceof ActivatedAbility) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
