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

package mage.cards.f;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public class FulgentDistraction extends CardImpl {

    public FulgentDistraction (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");


        this.getSpellAbility().addEffect(new FulgentDistractionEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(2));
    }

    public FulgentDistraction (final FulgentDistraction card) {
        super(card);
    }

    @Override
    public FulgentDistraction copy() {
        return new FulgentDistraction(this);
    }
}

class FulgentDistractionEffect extends OneShotEffect {

    private static String text = "Choose two target creatures. Tap those creatures, then unattach all Equipment from them";

    FulgentDistractionEffect ( ) {
        super(Outcome.Tap);
        staticText = text;
    }

    FulgentDistractionEffect ( FulgentDistractionEffect effect ) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for ( UUID target : targetPointer.getTargets(game, source) ) {
            Permanent creature = game.getPermanent(target);

            List<UUID> copiedAttachments = new ArrayList<>(creature.getAttachments());
            for ( UUID equipmentId : copiedAttachments ) {
                Permanent equipment = game.getPermanent(equipmentId);
                boolean isEquipment = false;

                for ( Ability ability : equipment.getAbilities() ) {
                    if ( ability instanceof EquipAbility ) {
                        isEquipment = true;
                    }
                }

                if ( isEquipment ) {
                    creature.removeAttachment(equipmentId, game);
                }
            }

            creature.tap(game);
        }
        return true;
    }

    @Override
    public FulgentDistractionEffect copy() {
        return new FulgentDistractionEffect(this);
    }

}
