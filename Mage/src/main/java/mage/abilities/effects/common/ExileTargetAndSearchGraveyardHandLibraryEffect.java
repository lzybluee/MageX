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
package mage.abilities.effects.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class ExileTargetAndSearchGraveyardHandLibraryEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {

    public ExileTargetAndSearchGraveyardHandLibraryEffect(boolean graveyardExileOptional, String searchWhatText, String searchForText) {
        super(graveyardExileOptional, searchWhatText, searchForText);
    }

    public ExileTargetAndSearchGraveyardHandLibraryEffect(final ExileTargetAndSearchGraveyardHandLibraryEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        UUID targetPlayerId = null;
        // get Target to exile
        Target exileTarget = null;
        for (Target target : source.getTargets()) {
            if (target instanceof TargetPermanent) {
                exileTarget = target;
                break;
            }
        }
        if (exileTarget != null) {
            Permanent permanentToExile = game.getPermanent(exileTarget.getFirstTarget());
            if (permanentToExile != null) {
                targetPlayerId = permanentToExile.getControllerId();
                result = permanentToExile.moveToExile(null, "", source.getSourceId(), game);
                this.applySearchAndExile(game, source, permanentToExile.getName(), targetPlayerId);
            }
        }

        return result;
    }

    @Override
    public ExileTargetAndSearchGraveyardHandLibraryEffect copy() {
        return new ExileTargetAndSearchGraveyardHandLibraryEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        sb.append("Exile target ").append(mode.getTargets().get(0).getTargetName()).append(". ");
        sb.append(super.getText(mode));
        return sb.toString();
    }
}
