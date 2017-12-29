/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list
 * of conditions and the following disclaimer in the documentation and/or other materials
 * provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package org.mage.test.cards.abilities.enters;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class SepulchralPrimordialTest extends CardTestPlayerBase {

    @Test
    public void testETB2Effect() {
        // When Sepulchral Primordial enters the battlefield, for each opponent, you may put up to one
        // target creature card from that player's graveyard onto the battlefield under your control.
        addCard(Zone.HAND, playerA, "Sepulchral Primordial", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 7);

        addCard(Zone.GRAVEYARD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sepulchral Primordial");
        addTarget(playerA, "Silvercoat Lion"); // target for ETB Sepulchral Primordial
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Sepulchral Primordial", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }

    /**
     * Sepulchral Primordial's "enter the battlefield" effect works correctly on
     * cast, but does not trigger if he is returned to the battlefield by other
     * means (e.g. summoned from the graveyard). I've encountered this in
     * 4-player commander games with other humans.
     */
    @Test
    public void testETBFromGraveyardEffect() {
        // Return target creature card from your graveyard to the battlefield. Put a +1/+1 counter on it.
        addCard(Zone.HAND, playerA, "Miraculous Recovery", 1); // Instant {4}{W}
        // When Sepulchral Primordial enters the battlefield, for each opponent, you may put up to one
        // target creature card from that player's graveyard onto the battlefield under your control.
        addCard(Zone.GRAVEYARD, playerA, "Sepulchral Primordial", 1); // Creature 5/4
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);

        addCard(Zone.GRAVEYARD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Miraculous Recovery", "Sepulchral Primordial");
        addTarget(playerA, "Silvercoat Lion"); // target for ETB Sepulchral Primordial
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Miraculous Recovery", 1);
        assertPowerToughness(playerA, "Sepulchral Primordial", 6, 5);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }
}
