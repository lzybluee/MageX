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
package org.mage.test.cards.abilities.abilitywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class DomainTest extends CardTestPlayerBase {

    /**
     * Collapsing Borders correctly does the 3 damage to each player at the
     * beginning of their upkeeps. However, it does NOT add any life for each
     * type of basic land the player has on the field.
     */
    @Test
    public void testCollapsingBorders() {
        // Domain - At the beginning of each player's upkeep, that player gains 1 life for each basic land type among lands he or she controls.
        // Then Collapsing Borders deals 3 damage to him or her.
        addCard(Zone.HAND, playerA, "Collapsing Borders", 1); // {3}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Collapsing Borders");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Collapsing Borders", 1);

        assertLife(playerA, 21);
        assertLife(playerB, 18);
    }

}
